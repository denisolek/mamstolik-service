package pl.denisolek.panel.restaurant.DTO.baseInfo

import pl.denisolek.core.address.Address

data class AddressDTO(
        var streetName: String = "",
        var buildingNumber: String = "",
        var postalCode: String = "",
        var latitude: Float = 0f,
        var longitude: Float = 0f,
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