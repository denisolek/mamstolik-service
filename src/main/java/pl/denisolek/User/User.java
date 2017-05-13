package pl.denisolek.User;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.denisolek.BaseEntity;
import pl.denisolek.Restaurant.Restaurant;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name="[user]")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {
    String email;

    String name;

    String surname;

    @OneToOne
    @JoinColumn(name = "restaurant_id")
    Restaurant restaurant;
}
