package pl.denisolek.panel.scheme

import io.swagger.annotations.Api
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.infrastructure.PANEL_BASE_PATH
import pl.denisolek.panel.scheme.DTO.SchemeDTO
import springfox.documentation.annotations.ApiIgnore

@Api("Scheme controller", tags = arrayOf("Scheme"))
@RequestMapping(PANEL_BASE_PATH)
interface PanelSchemeApi {
    companion object {
        const val RESTAURANT_ID: String = "restaurantId"

        const val SCHEME = "/{$RESTAURANT_ID}/scheme"
    }

    @GetMapping(SCHEME)
    @PreAuthorize("@authorizationService.currentUser.ownedRestaurants.contains(#restaurantId) || " +
            "@authorizationService.currentUser.workPlace == #restaurantId")
    fun getScheme(@ApiIgnore @PathVariable(RESTAURANT_ID) restaurantId: Restaurant): SchemeDTO
}