package pl.denisolek.core.customer

import pl.denisolek.core.BaseEntity
import javax.persistence.Entity

@Entity
class Customer(
        var email: String,
        var phoneNumber: String,
        var name: String,
        var surname: String
) : BaseEntity()