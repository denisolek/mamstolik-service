package pl.denisolek.panel.scheme.DTO.type

import pl.denisolek.core.scheme.SchemeItem

data class TypeItemDTO(
        var id: Int? = null,
        var floorId: Int? = null,
        var floorName: String,
        var subType: SchemeItem.ItemType,
        var position: SchemePositionDTO,
        var details: SchemeDetailsDTO
) {
    constructor(item: SchemeItem) : this(
            id = item.id,
            floorId = item.floor.id,
            floorName = item.floor.name,
            subType = item.itemType!!,
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