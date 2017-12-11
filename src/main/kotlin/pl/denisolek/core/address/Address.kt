package pl.denisolek.core.address

import javax.persistence.*

@Entity
data class Address(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null,
        var streetName: String = "",
        var buildingNumber: String = "",
        var postalCode: String = "",
        var latitude: Float = 0f,
        var longitude: Float = 0f,

        @OneToOne(cascade = arrayOf(CascadeType.ALL))
        var city: City? = null
)