package pl.denisolek.shared.image

import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import pl.denisolek.core.image.ImageService

@RestController
class ImageController(val imageService: ImageService) : ImageApi {
    companion object {
        val API = ImageApi.Companion
    }

    @ApiImplicitParams(
            ApiImplicitParam(name = API.IMAGE_TYPE, value = "Image type", paramType = "path", dataType = "string"),
            ApiImplicitParam(name = API.UUID, value = "Image uuid", paramType = "path", dataType = "string")
    )
    override fun getImage(@PathVariable(ImageApi.IMAGE_TYPE) imageType: String,
                          @PathVariable(ImageApi.UUID) uuid: String): ResponseEntity<Resource> {
        val file = imageService.loadAsResource(imageType, uuid)
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/png")
                .body(file)
    }
}