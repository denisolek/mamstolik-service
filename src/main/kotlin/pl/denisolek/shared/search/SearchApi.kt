package pl.denisolek.shared.search

import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import pl.denisolek.core.address.City
import pl.denisolek.core.config.API_BASE_PATH

@Api("SearchApi")
interface SearchApi {
    companion object {
        const val CITY_PATH = "$API_BASE_PATH/cities"
    }

    @GetMapping(CITY_PATH)
    fun getCities(@RequestParam name: String): List<City>
}