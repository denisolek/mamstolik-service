package pl.denisolek.Customer;

import org.springframework.stereotype.Component;

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
}
