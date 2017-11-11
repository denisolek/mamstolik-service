package pl.denisolek.panel.scheme

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.panel.scheme.DTO.SchemeDTO
import springfox.documentation.annotations.ApiIgnore

@RestController
class PanelSchemeController(val panelSchemeService: PanelSchemeService) : PanelSchemeApi {

    companion object {
        val API = PanelSchemeApi.Companion
    }

    override fun getScheme(@ApiIgnore @PathVariable(API.RESTAURANT_ID) restaurantId: Restaurant): SchemeDTO =
            panelSchemeService.getScheme(restaurantId)

}