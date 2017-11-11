package pl.denisolek.panel.schema

import io.swagger.annotations.Api
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.infrastructure.PANEL_BASE_PATH
import pl.denisolek.panel.schema.DTO.SchemaDTO
import springfox.documentation.annotations.ApiIgnore

@Api("Schema controller", tags = arrayOf("Schema"))
@RequestMapping(PANEL_BASE_PATH)
interface PanelSchemaApi {
    companion object {
        const val RESTAURANT_ID: String = "restaurantId"

        const val SCHEMA = "/{$RESTAURANT_ID}/schemas"
    }

    @GetMapping(SCHEMA)
    @PreAuthorize("@authorizationService.currentUser.ownedRestaurants.contains(#restaurantId) || " +
            "@authorizationService.currentUser.workPlace == #restaurantId")
    fun getSchema(@ApiIgnore @PathVariable(RESTAURANT_ID) restaurantId: Restaurant): SchemaDTO
}