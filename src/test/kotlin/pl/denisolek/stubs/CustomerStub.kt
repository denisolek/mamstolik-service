package pl.denisolek.stubs

import pl.denisolek.core.customer.Customer

class CustomerStub {
    companion object {
        fun getCustomer(): Customer =
                Customer(
                        email = "testcustomer@test.pl",
                        firstName = "Test",
                        lastName = "Testowy",
                        phoneNumber = "999999999"
                )
    }
}