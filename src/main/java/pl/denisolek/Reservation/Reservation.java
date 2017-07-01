package pl.denisolek.Reservation;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.denisolek.BaseEntity;
import pl.denisolek.Customer.Customer;
import pl.denisolek.Restaurant.Restaurant;
import pl.denisolek.Views;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Reservation extends BaseEntity {

	@ManyToOne
	@JsonView(Views.ReservationDetails.class)
	Restaurant restaurant;

	@ManyToOne
	@JoinColumn
	@JsonView(Views.Reservation.class)
	Customer customer;

	@JsonView(Views.Reservation.class)
	LocalDate date;

	@JsonView(Views.Reservation.class)
	LocalDateTime reservationBegin;

	@JsonView(Views.Reservation.class)
	Duration length;

	@JsonView(Views.Reservation.class)
	LocalDateTime reservationEnd;

	@JsonView(Views.Reservation.class)
	Integer peopleNumber;

	@JsonView(Views.Reservation.class)
	ReservationState state;

	Integer verificationCode;

	Boolean isVerified = false;
}
