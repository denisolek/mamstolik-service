package pl.denisolek.core.customer

import org.springframework.stereotype.Service

@Service
class CustomerService(private val customerRepository: CustomerRepository) {

    fun findOrCreate(customer: Customer): Customer =
        customerRepository.findByPhoneNumber(customer.phoneNumber) ?: customer

    fun save(customer: Customer): Customer =
        customerRepository.save(customer)
}