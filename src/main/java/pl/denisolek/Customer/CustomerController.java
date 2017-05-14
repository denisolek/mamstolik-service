package pl.denisolek.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class CustomerController implements CustomerApi {

	@Autowired
	CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@Override
	public List<Customer> getCustomers() {
		return customerService.getCustomers();
	}

	@Override
	public Customer getCustomer(@PathVariable("customerId") Customer customer) {
		return customerService.getCustomer(customer);
	}

	@Override
	public Customer addCustomer(@RequestBody Customer customer) {
		return customerService.addCustomer(customer);
	}
}
