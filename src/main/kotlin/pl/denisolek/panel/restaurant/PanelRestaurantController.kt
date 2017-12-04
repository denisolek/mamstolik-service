package pl.denisolek.panel.restaurant

import org.springframework.web.bind.annotation.RestController

@RestController
class PanelRestaurantController(val panelRestaurantService: PanelRestaurantService): PanelRestaurantApi {
    companion object {
        val API = PanelRestaurantApi.Companion
    }
}