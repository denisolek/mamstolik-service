package pl.denisolek.stubs

import pl.denisolek.core.address.Address

class AddressStub {
    companion object {
        fun getAddress(): Address =
                Address(
                        streetName = "Test street name",
                        buildingNumber = "1A",
                        latitude = 34.669252f,
                        longitude = -41.105359f,
                        postalCode = "66-800",
                        city = CityStub.getCity()
                )
    }
}