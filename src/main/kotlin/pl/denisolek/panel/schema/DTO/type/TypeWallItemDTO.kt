package pl.denisolek.panel.schema.DTO.type

import pl.denisolek.core.schema.SchemaItem
import pl.denisolek.core.schema.SchemaItem.WallItemType

data class TypeWallItemDTO(
        var id: Int? = null,
        var floorId: Int? = null,
        var subType: WallItemType,
        var position: SchemaPositionDTO,
        var details: SchemaDetailsDTO
) {
    constructor(item: SchemaItem) : this(
            id = item.id,
            floorId = item.floor.id,
            subType = item.wallItemType!!,
            details = SchemaDetailsDTO(
                    width = item.width,
                    heigth = item.height,
                    rotation = item.rotation
            ),
            position = SchemaPositionDTO(
                    x = item.x,
                    y = item.y
            )
    )
}