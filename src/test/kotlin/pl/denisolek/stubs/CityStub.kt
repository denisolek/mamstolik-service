package pl.denisolek.stubs

import pl.denisolek.core.address.City
import pl.denisolek.core.address.CityAlias

class CityStub {
    companion object {
        fun getCity(): City =
            City(
                name = "Test city",
                aliases = mutableListOf(CityAlias(name = "tc", city = getCity()))
            )
    }
}