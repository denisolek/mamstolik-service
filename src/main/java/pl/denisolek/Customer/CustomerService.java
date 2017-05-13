package pl.denisolek.Customer;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import pl.denisolek.Exception.ServiceException;

import java.util.List;

@Component
public class CustomerService {

	private final CustomerRepository customerRepository;

	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public List<Customer> getCustomers() {
		return customerRepository.findAll();
	}

	public Customer getCustomer(Customer customer) {
		return customer;
	}

	public Customer addCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	public Customer findOrCreate(Customer customer) {
		if (customer.getPhoneNumber() == null)
			throw new ServiceException(HttpStatus.BAD_REQUEST, "Phone number not provided");

		Customer processedCustomer = customerRepository.findByPhoneNumber(customer.getPhoneNumber());

		if (processedCustomer != null) {
			return processedCustomer;
		} else {
			return customerRepository.save(customer);
		}
	}
}
