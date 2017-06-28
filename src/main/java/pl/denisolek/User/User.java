package pl.denisolek.User;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.denisolek.BaseEntity;
import pl.denisolek.Restaurant.Restaurant;
import pl.denisolek.Views;

import javax.persistence.*;

@Data
@Entity
@Table(name = "[user]")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

	@Column(unique = true)
	@JsonView(Views.User.class)
	String email;

	@JsonView(Views.User.class)
	String name;

	@JsonView(Views.User.class)
	String surname;

	@JsonView(Views.User.class)
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "restaurant_id")
	Restaurant restaurant;

	@JsonView(Views.User.class)
	AccountState accountState;
}
