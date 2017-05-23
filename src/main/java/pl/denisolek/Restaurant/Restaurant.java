package pl.denisolek.Restaurant;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import pl.denisolek.BaseEntity;
import pl.denisolek.Reservation.Reservation;
import pl.denisolek.Views;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Restaurant extends BaseEntity {

	@JsonView(Views.Restaurant.class)
	String name;

	@JsonView(Views.Restaurant.class)
	String city;

	@JsonView(Views.Restaurant.class)
	String street;

	Float latitude;

	Float longitude;

	@JsonView(Views.RestaurantDetails.class)
	@Length(max = 3000)
	String description;

	Duration avgReservationTime;

	@JsonView(Views.Restaurant.class)
	Float rate;

	@JsonView(Views.Restaurant.class)
	Float service_rate;

	@JsonView(Views.Restaurant.class)
	Float place_rate;

	@JsonView(Views.Restaurant.class)
	Float price_quality_rate;

	String nip;

	Integer capacity;

	@JsonView(Views.Restaurant.class)
	Integer opinionCount;

	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
	List<Reservation> reservations;

	@JsonView(Views.Restaurant.class)
	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	@CollectionTable(name = "restaurant_kitchen", joinColumns = @JoinColumn(name = "restaurantId"))
	@Column(name = "kitchen_type", nullable = false)
	Set<KitchenType> kitchenTypes;

	@JsonView(Views.RestaurantDetails.class)
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "restaurant_business_hour", joinColumns = {@JoinColumn(name = "restaurant_id")}, inverseJoinColumns = {@JoinColumn(name = "business_hour_id")})
	Set<BusinessHour> businessHours = new HashSet<>();

	public Boolean isOpen(LocalTime searchDateStart, LocalTime searchDateEnd, LocalTime businessHourStart, LocalTime businessHourEnd) {
		if ((searchDateStart.isAfter(businessHourStart) || searchDateStart.equals(businessHourStart)) &&
				(searchDateEnd.isBefore(businessHourEnd) || searchDateEnd.equals(businessHourEnd)))
			return true;
		return false;
	}
}
