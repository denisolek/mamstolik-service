package pl.denisolek.Restaurant;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.denisolek.BaseEntity;
import pl.denisolek.User.User;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Restaurant extends BaseEntity{

    @ManyToOne
    @JoinColumn
    User user;

    String name;

    String city;

    String address;

    Integer latitude;

    Integer longitude;

    String description;

    Float rate;

    Float service_rate;

    Float place_rate;

    Float price_quality_rate;

    Integer nip;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "restaurant_kitchen", joinColumns = @JoinColumn(name = "restaurantId"))
    @Column(name = "kitchen_type", nullable = false)
    Set<KitchenType> kitchenTypes;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "restaurant_business_hour", joinColumns = { @JoinColumn(name = "restaurant_id") }, inverseJoinColumns = { @JoinColumn(name = "business_hour_id") })
    Set<BusinessHour> businessHours = new HashSet<>();
}
