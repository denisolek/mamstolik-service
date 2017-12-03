package pl.denisolek.panel.image

import io.swagger.annotations.Api
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import pl.denisolek.core.image.Image
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.infrastructure.PANEL_BASE_PATH
import pl.denisolek.panel.image.DTO.ImageDTO
import springfox.documentation.annotations.ApiIgnore

@Api("Image controller", tags = arrayOf("Image"))
@RequestMapping(PANEL_BASE_PATH)
interface PanelImageApi {
    companion object {
        const val RESTAURANT_ID: String = "restaurantId"
        const val IMAGE: String = "image"
        const val IMAGE_TYPE: String = "imageType"
        const val IMAGE_ID: String = "imageId"

        const val IMAGES_PATH = "/{$RESTAURANT_ID}/images"
        const val IMAGES_ID_PATH = "/{$RESTAURANT_ID}/images/{$IMAGE_ID}"
    }

    @PostMapping(IMAGES_PATH)
    @PreAuthorize("@authorizationService.currentUser.ownedRestaurants.contains(#restaurantId) || " +
            "@authorizationService.currentUser.workPlace == #restaurantId")
    fun uploadImage(@ApiIgnore @PathVariable(RESTAURANT_ID) restaurantId: Restaurant,
                    @RequestParam(value = IMAGE_TYPE, required = false, defaultValue = "regular") imageType: String,
                    @RequestParam(value = IMAGE, required = true) image: MultipartFile): ImageDTO

    @DeleteMapping(IMAGES_ID_PATH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("@authorizationService.currentUser.ownedRestaurants.contains(#restaurantId) || " +
            "@authorizationService.currentUser.workPlace == #restaurantId")
    fun removeImage(@ApiIgnore @PathVariable(RESTAURANT_ID) restaurantId: Restaurant,
                    @ApiIgnore @PathVariable(IMAGE_ID) imageId: Image)
}