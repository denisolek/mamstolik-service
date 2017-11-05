package pl.denisolek.shared.search

import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import pl.denisolek.infrastructure.API_BASE_PATH
import pl.denisolek.shared.search.dto.CitiesRestaurantsDTO

@Api("Search controller", tags = arrayOf("Search"))
@RequestMapping(API_BASE_PATH)
interface SearchApi {
    companion object {
        const val SEARCH_PATH = "/search"
    }

    @GetMapping(SEARCH_PATH)
    fun findCitiesAndRestaurants(@RequestParam name: String): CitiesRestaurantsDTO
}