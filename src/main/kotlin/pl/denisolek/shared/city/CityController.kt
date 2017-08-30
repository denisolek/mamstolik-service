package pl.denisolek.shared.city

import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.denisolek.core.address.City
import pl.denisolek.core.address.CityService

@RestController
class CityController(val cityService: CityService) : CityApi {
    override fun getCities(@RequestParam name: String): List<City> =
            cityService.getCities(name)
}