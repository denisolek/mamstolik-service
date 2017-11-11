package pl.denisolek.panel.schema.DTO.type

import pl.denisolek.core.schema.SchemaItem

data class TypeWallDTO(
        var id: Int? = null,
        var floorId: Int? = null,
        var floorName: String,
        var position: SchemaPositionDTO,
        var details: SchemaDetailsDTO
) {
    constructor(item: SchemaItem) : this(
            id = item.id,
            floorId = item.floor.id,
            floorName = item.floor.name,
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