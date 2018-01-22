package pl.denisolek.shared.image

import io.swagger.annotations.Api
import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Api("Image controller", tags = arrayOf("Image"))
interface ImageApi {
    companion object {
        const val IMAGE_TYPE = "imageType"
        const val UUID = "uuid"

        const val IMAGES_PATH = "/images"
        const val IMAGES_UUID_PATH = "$IMAGES_PATH/{$IMAGE_TYPE}/{$UUID}"
    }

    @GetMapping(IMAGES_UUID_PATH)
    fun getImage(
        @PathVariable(IMAGE_TYPE) imageType: String,
        @PathVariable(UUID) uuid: String
    ): ResponseEntity<Resource>
}