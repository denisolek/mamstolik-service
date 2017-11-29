package pl.denisolek.core.customer

import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository : JpaRepository<Customer, Int> {
    fun findByPhoneNumber(phoneNumber: String): Customer?
}