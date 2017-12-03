package pl.denisolek.panel.image

import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.RequestMapping
import pl.denisolek.infrastructure.PANEL_BASE_PATH

@Api("Image controller", tags = arrayOf("Image"))
@RequestMapping(PANEL_BASE_PATH)
interface PanelImageApi {
    companion object {
        const val RESTAURANT_ID: String = "restaurantId"

        const val IMAGE_PATH = "/{$RESTAURANT_ID}/images"
    }
}