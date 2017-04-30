package pl.denisolek.Customer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "customer", description = "the customer API", tags = "customer")
public interface CustomerApi {
	String BASE_PATH = "/customers";

	@ApiOperation(value = "Get all customers", response = Customer.class, responseContainer = "List")
	@ResponseBody
	@RequestMapping(value = BASE_PATH, method = RequestMethod.GET)
	List<Customer> getCustomers();

	@ApiOperation(value = "Get customer by id", response = Customer.class)
	@ResponseBody
	@RequestMapping(value = BASE_PATH + "/{customerId}", method = RequestMethod.GET)
	Customer getCustomer(@PathVariable("customerId") Customer customer);

	@ApiOperation(value = "Add customer", response = Customer.class)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	@RequestMapping(value = BASE_PATH, method = RequestMethod.POST)
	Customer addCustomer(@RequestBody Customer customer);
}
