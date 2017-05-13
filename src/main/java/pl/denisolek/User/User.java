package pl.denisolek.User;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.denisolek.BaseEntity;
import pl.denisolek.Restaurant.Restaurant;

import javax.persistence.*;

@Data
@Entity
@Table(name="[user]")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {
    String email;

    String name;

    String surname;

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    Restaurant restaurant;
}
