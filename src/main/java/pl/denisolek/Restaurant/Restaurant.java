package pl.denisolek.Restaurant;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.denisolek.BaseEntity;
import pl.denisolek.Reservation.Reservation;
import pl.denisolek.User.User;

import javax.persistence.*;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Restaurant extends BaseEntity{

    String name;

    String city;

    @OneToOne
    @JoinColumn(name = "user_id")
    User user;

    String street;

    Float latitude;

    Float longitude;

    String description;

    Duration avgReservationTime;

    Float rate;

    Float service_rate;

    Float place_rate;

    Float price_quality_rate;

    String nip;

    Integer capacity;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Reservation> reservations;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "restaurant_kitchen", joinColumns = @JoinColumn(name = "restaurantId"))
    @Column(name = "kitchen_type", nullable = false)
    Set<KitchenType> kitchenTypes;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "restaurant_business_hour", joinColumns = { @JoinColumn(name = "restaurant_id") }, inverseJoinColumns = { @JoinColumn(name = "business_hour_id") })
    Set<BusinessHour> businessHours = new HashSet<>();
}
