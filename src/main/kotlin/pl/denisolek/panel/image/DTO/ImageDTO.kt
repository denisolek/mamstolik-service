package pl.denisolek.panel.image.DTO

import pl.denisolek.core.image.Image

data class ImageDTO(
        var id: Int,
        var uuid: String,
        var isMain: Boolean
) {
    companion object {
        fun fromImage(image: Image) = ImageDTO(
                id = image.id!!,
                uuid = image.uuid,
                isMain = image.restaurant?.mainImage == image
        )
    }
}
