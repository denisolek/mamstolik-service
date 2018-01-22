package pl.denisolek.panel.restaurant.DTO.baseInfo

import pl.denisolek.core.address.Address
import javax.validation.constraints.Pattern

data class AddressDTO(
    var streetName: String = "",
    var buildingNumber: String = "",
    var postalCode: String = "",
    var latitude: Float = 0f,
    var longitude: Float = 0f,
    @field:Pattern(
        regexp = "^[a-z A-Z0-9żźćńółęąśŻŹĆĄŚĘŁÓŃ]+(-[a-z A-Z0-9żźćńółęąśŻŹĆĄŚĘŁÓŃ]+)?\$",
        message = "Name accepts a-z A-Z 0-9 - and spaces."
    )
    var city: String = ""
) {
    companion object {
        fun fromAddress(address: Address?) = AddressDTO(
            streetName = address?.streetName ?: "",
            buildingNumber = address?.buildingNumber ?: "",
            postalCode = address?.postalCode ?: "",
            latitude = address?.latitude ?: 1f,
            longitude = address?.longitude ?: 1f,
            city = address?.city?.name ?: ""
        )
    }
}