package pl.denisolek.panel.schema

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.panel.schema.DTO.SchemaDTO
import springfox.documentation.annotations.ApiIgnore

@RestController
class PanelSchemaController(val panelSchemaService: PanelSchemaService) : PanelSchemaApi {

    companion object {
        val API = PanelSchemaApi.Companion
    }

    override fun getSchema(@ApiIgnore @PathVariable(API.RESTAURANT_ID) restaurantId: Restaurant): SchemaDTO =
            panelSchemaService.getSchema(restaurantId)

}