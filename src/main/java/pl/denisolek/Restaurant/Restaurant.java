package pl.denisolek.Restaurant;

import lombok.Data;
import pl.denisolek.BaseEntity;
import pl.denisolek.User.User;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
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
}
