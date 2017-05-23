package pl.denisolek.User;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.denisolek.BaseEntity;
import pl.denisolek.Restaurant.Restaurant;

import javax.persistence.*;

@Data
@Entity
@Table(name = "[user]")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

	@Column(unique = true)
	String email;

	String name;

	String surname;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "restaurant_id")
	Restaurant restaurant;
}
