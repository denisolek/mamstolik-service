package pl.denisolek.core.infrastructure.exceptions.customer

import org.hibernate.validator.constraints.Email
import pl.denisolek.core.BaseEntity
import javax.persistence.Entity

@Entity
class Customer(
        @Email
        var email: String,

        var phoneNumber: String,

        var name: String,

        var surname: String
) : BaseEntity()