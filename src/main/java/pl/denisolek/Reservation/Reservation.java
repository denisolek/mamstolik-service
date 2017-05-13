package pl.denisolek.Reservation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.denisolek.BaseEntity;
import pl.denisolek.Customer.Customer;
import pl.denisolek.Restaurant.Restaurant;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.Duration;
import java.time.LocalDateTime;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Reservation extends BaseEntity {

	@ManyToOne
	@JsonIgnore
	Restaurant restaurant;

	@ManyToOne
	@JoinColumn
	Customer customer;

	LocalDateTime reservationBegin;

	Duration length;

	LocalDateTime reservationEnd;

	Integer peopleNumber;

	ReservationState state;
}
