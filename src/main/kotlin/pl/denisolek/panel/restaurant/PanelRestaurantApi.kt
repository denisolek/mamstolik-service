package pl.denisolek.panel.restaurant

import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.RequestMapping
import pl.denisolek.infrastructure.PANEL_BASE_PATH

@Api("Restaurant controller", tags = arrayOf("Restaurant"))
@RequestMapping(PANEL_BASE_PATH)
interface PanelRestaurantApi {
    companion object {
        const val RESTAURANT_ID: String = "restaurantId"

        const val DETAILS_PATH = "/{$RESTAURANT_ID}/details"
        const val BASE_INFO_PATH = "/{$RESTAURANT_ID}/baseInfo"
        const val PROFILE_PATH = "/{$RESTAURANT_ID}/profile"
    }
}