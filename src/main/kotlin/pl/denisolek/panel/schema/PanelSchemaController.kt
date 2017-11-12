package pl.denisolek.panel.schema

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.schema.Floor
import pl.denisolek.panel.schema.DTO.FloorDTO
import pl.denisolek.panel.schema.DTO.SchemaDTO
import springfox.documentation.annotations.ApiIgnore
import javax.validation.Valid

@RestController
class PanelSchemaController(val panelSchemaService: PanelSchemaService) : PanelSchemaApi {

    companion object {
        val API = PanelSchemaApi.Companion
    }

    override fun getSchema(@ApiIgnore @PathVariable(API.RESTAURANT_ID) restaurantId: Restaurant): SchemaDTO =
            panelSchemaService.getSchema(restaurantId)

    override fun addFloor(@ApiIgnore @PathVariable(API.RESTAURANT_ID) restaurantId: Restaurant,
                          @RequestBody @Valid floorDTO: FloorDTO): SchemaDTO =
            panelSchemaService.addFloor(restaurantId, floorDTO)

    override fun deleteFloor(@ApiIgnore @PathVariable(API.RESTAURANT_ID) restaurantId: Restaurant,
                             @ApiIgnore @PathVariable(API.FLOOR_ID) floorId: Floor): SchemaDTO =
            panelSchemaService.deleteFloor(restaurantId, floorId)
}