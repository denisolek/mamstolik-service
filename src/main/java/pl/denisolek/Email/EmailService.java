package pl.denisolek.Email;

import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.denisolek.Reservation.Reservation;

@Component
public class EmailService {
	private final EmailSender emailSender;
	private final TemplateEngine templateEngine;

	public EmailService(EmailSender emailSender, TemplateEngine templateEngine) {
		this.emailSender = emailSender;
		this.templateEngine = templateEngine;
	}

//	public void welcomeEmail(String targetEmail) throws UnsupportedEncodingException, MessagingException {
//		Context context = new Context();
//		String body = templateEngine.process("welcome", context);
//		emailSender.sendEmail(targetEmail, "Dziękujemy za subskrypcję!", body);
//	}

	public void reservationAccepted(Reservation reservation) {
		Context context = new Context();
		context.setVariable("name", reservation.getCustomer().getName());
		context.setVariable("restaurantName", reservation.getRestaurant().getName());
		context.setVariable("day", reservation.getReservationBegin().toLocalDate().toString());
		context.setVariable("hour", reservation.getReservationBegin().toLocalTime().toString());
		context.setVariable("peopleCount", reservation.getPeopleNumber());

		String body = templateEngine.process("reservation-accepted", context);
		emailSender.sendEmail(reservation.getCustomer().getEmail(), "Potwierdzenie rezerwacji.", body);
	}
}
