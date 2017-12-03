package pl.denisolek.panel.schema

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.floor.Floor
import pl.denisolek.core.spot.Spot
import pl.denisolek.panel.schema.DTO.FloorDTO
import pl.denisolek.panel.schema.DTO.SchemaDTO
import pl.denisolek.panel.schema.DTO.type.SchemaSpotInfoDTO
import springfox.documentation.annotations.ApiIgnore
import javax.validation.Valid

@RestController
class PanelSchemaController(val panelSchemaService: PanelSchemaService) : PanelSchemaApi {
    companion object {
        val API = PanelSchemaApi.Companion
    }

    override fun getSchema(@ApiIgnore @PathVariable(API.RESTAURANT_ID) restaurantId: Restaurant): SchemaDTO =
            panelSchemaService.getSchema(restaurantId)

    override fun updateSchema(@ApiIgnore @PathVariable(PanelSchemaApi.RESTAURANT_ID) restaurantId: Restaurant,
                              @RequestBody schemaDTO: SchemaDTO): SchemaDTO =
            panelSchemaService.updateSchema(restaurantId, schemaDTO)

    override fun addFloor(@ApiIgnore @PathVariable(API.RESTAURANT_ID) restaurantId: Restaurant,
                          @RequestBody @Valid floorDTO: FloorDTO): FloorDTO =
            panelSchemaService.addFloor(restaurantId, floorDTO)

    override fun deleteFloor(@ApiIgnore @PathVariable(API.RESTAURANT_ID) restaurantId: Restaurant,
                             @ApiIgnore @PathVariable(API.FLOOR_ID) floorId: Floor) =
            panelSchemaService.deleteFloor(restaurantId, floorId)

    override fun updateSpot(@ApiIgnore @PathVariable(PanelSchemaApi.RESTAURANT_ID) restaurantId: Restaurant,
                            @ApiIgnore @PathVariable(PanelSchemaApi.SPOT_ID) spotId: Spot,
                            @RequestBody @Valid spotInfoDTO: SchemaSpotInfoDTO): SchemaDTO =
            panelSchemaService.updateSpot(restaurantId, spotId, spotInfoDTO)

    override fun deleteSpot(@ApiIgnore @PathVariable(PanelSchemaApi.RESTAURANT_ID) restaurantId: Restaurant,
                            @ApiIgnore @PathVariable(PanelSchemaApi.SPOT_ID) spotId: Spot): SchemaDTO =
            panelSchemaService.deleteSpot(restaurantId, spotId)

}