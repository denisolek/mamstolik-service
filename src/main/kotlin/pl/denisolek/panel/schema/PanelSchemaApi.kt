package pl.denisolek.panel.schema

import io.swagger.annotations.Api
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.schema.Floor
import pl.denisolek.core.spot.Spot
import pl.denisolek.infrastructure.PANEL_BASE_PATH
import pl.denisolek.panel.schema.DTO.FloorDTO
import pl.denisolek.panel.schema.DTO.SchemaDTO
import pl.denisolek.panel.schema.DTO.type.SchemaSpotInfoDTO
import springfox.documentation.annotations.ApiIgnore
import javax.validation.Valid

@Api("Schema controller", tags = arrayOf("Schema"))
@RequestMapping(PANEL_BASE_PATH)
interface PanelSchemaApi {
    companion object {
        const val RESTAURANT_ID: String = "restaurantId"
        const val FLOOR_ID: String = "floorId"
        const val SPOT_ID: String = "spotId"

        const val SCHEMAS_PATH = "/{$RESTAURANT_ID}/schemas"
        const val FLOORS_PATH = "$SCHEMAS_PATH/floors"
        const val FLOORS_ID_PATH = "$SCHEMAS_PATH/floors/{$FLOOR_ID}"
        const val SPOTS_ID_PATH = "$SCHEMAS_PATH/spots/{$SPOT_ID}"
    }

    @GetMapping(SCHEMAS_PATH)
    @PreAuthorize("@authorizationService.currentUser.ownedRestaurants.contains(#restaurantId) || " +
            "@authorizationService.currentUser.workPlace == #restaurantId")
    fun getSchema(@ApiIgnore @PathVariable(RESTAURANT_ID) restaurantId: Restaurant): SchemaDTO

    @PutMapping(SCHEMAS_PATH)
    @PreAuthorize("@authorizationService.currentUser.ownedRestaurants.contains(#restaurantId) || " +
            "@authorizationService.currentUser.workPlace == #restaurantId")
    fun updateSchema(@ApiIgnore @PathVariable(RESTAURANT_ID) restaurantId: Restaurant,
                     @RequestBody @Valid schemaDTO: SchemaDTO): SchemaDTO

    @PostMapping(FLOORS_PATH)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("@authorizationService.currentUser.ownedRestaurants.contains(#restaurantId) || " +
            "@authorizationService.currentUser.workPlace == #restaurantId")
    fun addFloor(@ApiIgnore @PathVariable(RESTAURANT_ID) restaurantId: Restaurant,
                 @RequestBody @Valid floorDTO: FloorDTO): SchemaDTO

    @DeleteMapping(FLOORS_ID_PATH)
    @PreAuthorize("@authorizationService.currentUser.ownedRestaurants.contains(#restaurantId) || " +
            "@authorizationService.currentUser.workPlace == #restaurantId")
    fun deleteFloor(@ApiIgnore @PathVariable(RESTAURANT_ID) restaurantId: Restaurant,
                    @ApiIgnore @PathVariable(FLOOR_ID) floorId: Floor): SchemaDTO

    @PutMapping(SPOTS_ID_PATH)
    @PreAuthorize("@authorizationService.currentUser.ownedRestaurants.contains(#restaurantId) || " +
            "@authorizationService.currentUser.workPlace == #restaurantId")
    fun updateSpot(@ApiIgnore @PathVariable(RESTAURANT_ID) restaurantId: Restaurant,
                   @ApiIgnore @PathVariable(SPOT_ID) spotId: Spot,
                   @RequestBody @Valid spotInfoDTO: SchemaSpotInfoDTO): SchemaDTO

    @DeleteMapping(SPOTS_ID_PATH)
    @PreAuthorize("@authorizationService.currentUser.ownedRestaurants.contains(#restaurantId) || " +
            "@authorizationService.currentUser.workPlace == #restaurantId")
    fun deleteSpot(@ApiIgnore @PathVariable(RESTAURANT_ID) restaurantId: Restaurant,
                   @ApiIgnore @PathVariable(SPOT_ID) spotId: Spot): SchemaDTO
}