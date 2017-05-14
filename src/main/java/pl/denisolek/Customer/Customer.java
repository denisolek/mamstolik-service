package pl.denisolek.Customer;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Email;
import pl.denisolek.BaseEntity;

import javax.persistence.Entity;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Customer extends BaseEntity{

	@Email
	String email;

	String phoneNumber;

	String name;

	String surname;
}
