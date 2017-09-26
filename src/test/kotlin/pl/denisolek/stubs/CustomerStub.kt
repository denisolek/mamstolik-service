package pl.denisolek.stubs

import pl.denisolek.core.customer.Customer

class CustomerStub {
    companion object {
        fun getCustomer(): Customer =
                Customer(
                        email = "testcustomer@test.pl",
                        name = "Test",
                        surname = "Testowy",
                        phoneNumber = "999999999"
                )
    }
}