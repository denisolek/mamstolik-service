package pl.denisolek.core.address

import pl.denisolek.core.BaseEntity
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.OneToOne

@Entity
data class Address(
        var streetName: String,
        var buildingNumber: String,
        var postalCode: String,
        var latitude: Float,
        var longitude: Float,

        @OneToOne(cascade = arrayOf(CascadeType.ALL))
        var city: City
) : BaseEntity()