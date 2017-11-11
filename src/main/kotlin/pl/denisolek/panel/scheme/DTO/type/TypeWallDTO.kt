package pl.denisolek.panel.scheme.DTO.type

import pl.denisolek.core.scheme.SchemeItem

data class TypeWallDTO(
        var id: Int? = null,
        var floorId: Int? = null,
        var floorName: String,
        var position: SchemePositionDTO,
        var details: SchemeDetailsDTO
) {
    constructor(item: SchemeItem) : this(
            id = item.id,
            floorId = item.floor.id,
            floorName = item.floor.name,
            details = SchemeDetailsDTO(
                    width = item.width,
                    heigth = item.height,
                    rotation = item.rotation
            ),
            position = SchemePositionDTO(
                    x = item.x,
                    y = item.y
            )
    )
}