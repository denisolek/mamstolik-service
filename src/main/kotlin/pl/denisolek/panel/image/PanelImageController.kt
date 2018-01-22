package pl.denisolek.panel.image

import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import pl.denisolek.core.image.Image
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.panel.image.DTO.ImageDTO
import springfox.documentation.annotations.ApiIgnore

@RestController
class PanelImageController(val panelImageService: PanelImageService) : PanelImageApi {
    companion object {
        val API = PanelImageApi.Companion
    }

    @ApiImplicitParams(
        ApiImplicitParam(
            name = API.RESTAURANT_ID,
            value = "Restaurant Id",
            paramType = "path",
            dataType = "int",
            required = true
        ),
        ApiImplicitParam(
            name = API.IMAGE_TYPE,
            value = "Image Type",
            paramType = "query",
            dataType = "string",
            required = true
        ),
        ApiImplicitParam(name = API.IMAGE, value = "Image", paramType = "query", dataType = "object", required = true)
    )
    override fun uploadImage(
        @ApiIgnore @PathVariable(API.RESTAURANT_ID) restaurantId: Restaurant,
        @RequestParam(value = API.IMAGE_TYPE, required = true, defaultValue = "regular") imageType: String,
        @RequestParam(value = API.IMAGE, required = true) image: MultipartFile
    ): ImageDTO =
        panelImageService.uploadImage(restaurantId, imageType, image)

    @ApiImplicitParams(
        ApiImplicitParam(
            name = API.RESTAURANT_ID,
            value = "Restaurant Id",
            paramType = "path",
            dataType = "int",
            required = true
        ),
        ApiImplicitParam(name = API.IMAGE_ID, value = "Image Id", paramType = "path", dataType = "int", required = true)
    )
    override fun removeImage(
        @ApiIgnore @PathVariable(PanelImageApi.RESTAURANT_ID) restaurantId: Restaurant,
        @ApiIgnore @PathVariable(PanelImageApi.IMAGE_ID) imageId: Image
    ) =
        panelImageService.removeImage(restaurantId, imageId)

}