package pl.denisolek.panel.employee.DTO

import pl.denisolek.core.image.Image

data class AvatarDTO(
        var id: Int,
        var uuid: String
) {
    companion object {
        fun fromImage(image: Image) = AvatarDTO(
                id = image.id!!,
                uuid = image.uuid
        )
    }
}