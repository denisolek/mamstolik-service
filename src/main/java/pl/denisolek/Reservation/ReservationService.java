package pl.denisolek.Reservation;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import pl.denisolek.Customer.Customer;
import pl.denisolek.Customer.CustomerService;
import pl.denisolek.Email.EmailService;
import pl.denisolek.Exception.ServiceException;
import pl.denisolek.Restaurant.BusinessHour;
import pl.denisolek.Restaurant.Restaurant;
import pl.denisolek.User.AvailableCapacityAtDate;
import pl.denisolek.Utils.Tools;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class ReservationService {

	@Autowired
	CustomerService customerService;

	@Autowired
	Tools tools;

	@Autowired
	EmailService emailService;

	@Autowired
	private RabbitTemplate template;

	@Autowired
	private Queue smsQueue;

	private final ReservationRepository reservationRepository;
	private final Integer CHECKING_INTERVAL = 15;
	private final Integer CODE_MIN = 100000;
	private final Integer CODE_MAX = 999999;

	public ReservationService(ReservationRepository reservationRepository) {
		this.reservationRepository = reservationRepository;
	}

	public List<Reservation> getRestaurantReservations(Integer restaurantId) {
		return reservationRepository.findByRestaurantIdAndIsVerified(restaurantId, true);
	}

	public Reservation getReservation(Reservation reservation) {
		if (reservation == null)
			throw new ServiceException(HttpStatus.NOT_FOUND, "Reservation not found");

		return reservation;
	}

	public Reservation addReservation(Restaurant restaurant, Reservation reservation) {
		validateReservationRequest(restaurant, reservation);
		Duration duration = restaurant.getAvgReservationTime();

		reservation.setLength(duration);
		reservation.setReservationEnd(reservation.getReservationBegin().plus(reservation.getLength()));

		BusinessHour businessHour = tools.getDateBusinessHour(restaurant.getBusinessHours(), reservation.getReservationBegin().toLocalDate());
		if (!tools.isContaining(reservation.getReservationBegin().toLocalTime(), reservation.getReservationEnd().toLocalTime(), businessHour.getOpen(), businessHour.getClose()))
			throw new ServiceException(HttpStatus.BAD_REQUEST, "Can't make reservation if restaurant is closed");

		List<LocalDateTime> checkIntervals = new ArrayList<>();
		LocalDateTime startSearchDate = reservation.getReservationBegin().minus(duration);
		LocalDateTime endSearchDate = reservation.getReservationEnd().plus(duration);

		tools.getDatesToCheck(checkIntervals, reservation.getReservationBegin(), reservation.getLength(), CHECKING_INTERVAL);
		checkAvailableSpotsCount(restaurant, reservation, duration, checkIntervals, startSearchDate, endSearchDate);

		Customer currentCustomer = customerService.findOrCreate(reservation.getCustomer());
		reservation.setDate(reservation.getReservationBegin().toLocalDate());
		reservation.setCustomer(currentCustomer);
		reservation.setRestaurant(restaurant);
		reservation.setState(ReservationState.PENDING);
		reservation.setVerificationCode(generateCode());

		sendSmsCode(reservation);
		return reservationRepository.save(reservation);
	}

	private Integer generateCode() {
		Random random = new Random();
		return random.nextInt((CODE_MAX - CODE_MIN) + 1) + CODE_MIN;
	}

	private void sendSmsCode(Reservation reservation) {
		SmsMessage message = new SmsMessage(reservation.getVerificationCode(), reservation.getCustomer().getPhoneNumber());
		this.template.convertAndSend(smsQueue.getName(), message);
		System.out.println(" [RabbitMQ] Sent '" + message + "'");
	}

	private void validateReservationRequest(Restaurant restaurant, Reservation reservation) {
		if (restaurant == null)
			throw new ServiceException(HttpStatus.NOT_FOUND, "Restaurant not found");

		if (reservation.getReservationBegin() == null || reservation.getPeopleNumber() == null || reservation.getCustomer() == null)
			throw new ServiceException(HttpStatus.BAD_REQUEST, "Reservation time, people number or customer not provided");

		if (reservation.getPeopleNumber() < 1)
			throw new ServiceException(HttpStatus.BAD_REQUEST, "People number can't be lower than 1");

		if (LocalDateTime.now(ZoneId.of("Poland")).isAfter(reservation.getReservationBegin()))
			throw new ServiceException(HttpStatus.BAD_REQUEST, "You cant make reservation in the past");
	}

	private void checkAvailableSpotsCount(Restaurant restaurant, Reservation reservation, Duration duration, List<LocalDateTime> checkIntervals, LocalDateTime startSearchDate, LocalDateTime endSearchDate) {
		List<Reservation> reservationsOverlapping = getReservationsBetween(startSearchDate, endSearchDate, restaurant.getId());
		for (int i = 0; i < checkIntervals.size(); i++) {
			Integer spotsTaken = 0;
			for (int j = 0; j < reservationsOverlapping.size(); j++) {
				if (tools.isBetween(checkIntervals.get(i), reservationsOverlapping.get(j).getReservationBegin(), duration)) {
					spotsTaken += reservationsOverlapping.get(j).getPeopleNumber();
				}
			}
			if (spotsTaken + reservation.getPeopleNumber() > restaurant.getCapacity()) {
				throw new ServiceException(HttpStatus.CONFLICT, "Not enought free spots in selected restaurant");
			}
		}
	}

	public List<Reservation> getReservationsBetween(LocalDateTime begin, LocalDateTime end, Integer restaurantId) {
		return reservationRepository.findByReservationBeginGreaterThanEqualAndReservationEndIsLessThanAndRestaurantId(begin, end, restaurantId);
	}

	public List<Reservation> getReservationsAtDate(LocalDate date, Integer restaurantId) {
		return reservationRepository.findByDateAndRestaurantId(date, restaurantId);
	}

	public List<AvailableCapacityAtDate> getRestaurantCapacityAtDate(LocalDate date, Restaurant restaurant) {
		List<Reservation> reservations = getReservationsAtDate(date, restaurant.getId());
		List<AvailableCapacityAtDate> capacityList = new ArrayList<>();

		BusinessHour businessHour = tools.getDateBusinessHour(restaurant.getBusinessHours(), date);

		if (businessHour == null)
			throw new ServiceException(HttpStatus.BAD_REQUEST, "Restaurant is closed this day");

		LocalDateTime dayStart = LocalDateTime.of(date, businessHour.getOpen());
		LocalDateTime dayEnd = LocalDateTime.of(date, businessHour.getClose());
		LocalDateTime checkingInterval = dayStart;

		fillCapacityList(restaurant, reservations, capacityList, dayEnd, checkingInterval);
		return capacityList;
	}

	private void fillCapacityList(Restaurant restaurant, List<Reservation> reservations, List<AvailableCapacityAtDate> capacityList, LocalDateTime dayEnd, LocalDateTime checkingInterval) {
		while (dayEnd.isAfter(checkingInterval)) {
			Integer availableCapacity = restaurant.getCapacity();
			for (int i = 0; i < reservations.size(); i++) {
				if (tools.isBetween(checkingInterval, reservations.get(i).getReservationBegin(), reservations.get(i).getLength())) {
					availableCapacity -= reservations.get(i).getPeopleNumber();
				}
			}
			capacityList.add(new AvailableCapacityAtDate(checkingInterval, availableCapacity));
			checkingInterval = checkingInterval.plusMinutes(CHECKING_INTERVAL);
		}
	}

	public List<Reservation> getReservations() {
		return reservationRepository.findAll();
	}

	public Reservation changeReservationState(Reservation reservation, Reservation updatedReservation) {
		if (reservation == null)
			throw new ServiceException(HttpStatus.NOT_FOUND, "Reservation not found");

		if (updatedReservation.getState() == null)
			throw new ServiceException(HttpStatus.BAD_REQUEST, "Incorrect state format");

		reservation.setState(updatedReservation.getState());

		switch (reservation.getState()) {
			case ACCEPTED:
				new Thread(()->{
					emailService.reservationAccepted(reservation);
				}).start();
				break;
			case CANCELED:
				new Thread(()->{
					emailService.reservationCanceled(reservation);
				}).start();
				break;
		}
		return reservationRepository.save(reservation);
	}

	public void checkVerificationCode(Reservation reservation, String code) {
		if (reservation == null)
			throw new ServiceException(HttpStatus.NOT_FOUND, "Reservation not found.");

		if (reservation.getIsVerified())
			throw new ServiceException(HttpStatus.CONFLICT, "Already verified.");

		if (!reservation.getVerificationCode().equals(Integer.parseInt(code)))
			throw new ServiceException(HttpStatus.BAD_REQUEST, "Invalid authorization code.");

		reservation.setIsVerified(true);
		reservationRepository.save(reservation);

	}
}
