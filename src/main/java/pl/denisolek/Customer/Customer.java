package pl.denisolek.Customer;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Email;
import pl.denisolek.BaseEntity;
import pl.denisolek.Views;

import javax.persistence.Entity;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Customer extends BaseEntity {

	@Email
	@JsonView(Views.Customer.class)
	String email;

	@JsonView(Views.Customer.class)
	String phoneNumber;

	@JsonView(Views.Customer.class)
	String name;

	@JsonView(Views.Customer.class)
	String surname;
}
