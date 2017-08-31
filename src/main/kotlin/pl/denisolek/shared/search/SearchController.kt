package pl.denisolek.shared.search

import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.denisolek.core.address.City
import pl.denisolek.core.address.CityService

@RestController
class SearchController(val cityService: CityService) : SearchApi {
    override fun getCities(@RequestParam name: String): List<City> =
            cityService.getCities(name)
}