package pl.denisolek.Reservation;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.denisolek.BaseEntity;
import pl.denisolek.Customer.Customer;
import pl.denisolek.Restaurant.Restaurant;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Reservation extends BaseEntity {

	@ManyToOne
	@JoinColumn
	Restaurant restaurant;

	@ManyToOne
	@JoinColumn
	Customer customer;

	Timestamp reservationBegin;

	Timestamp reservationEnd;

	Integer peopleNumber;

	ReservationState state;
}
