package pl.denisolek.shared.search

import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.denisolek.shared.search.dto.CitiesRestaurantsDTO

@RestController
class SearchController(val searchService: SearchService) : SearchApi {
    override fun findCitiesAndRestaurants(@RequestParam name: String): CitiesRestaurantsDTO =
        searchService.findCitiesAndRestaurants(name)
}