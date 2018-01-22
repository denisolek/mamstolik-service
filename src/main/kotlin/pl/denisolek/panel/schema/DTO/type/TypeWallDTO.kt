package pl.denisolek.panel.schema.DTO.type

import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.schema.SchemaItem
import pl.denisolek.core.schema.SchemaItem.Type.WALL

data class TypeWallDTO(
    var id: Int? = null,
    var floorId: Int,
    var position: SchemaPositionDTO,
    var details: SchemaDetailsDTO
) {
    constructor(item: SchemaItem) : this(
        id = item.id,
        floorId = item.floor.id!!,
        details = SchemaDetailsDTO(
            width = item.width,
            height = item.height,
            rotation = item.rotation
        ),
        position = SchemaPositionDTO(
            x = item.x,
            y = item.y
        )
    )

    companion object {
        fun toSchemaItem(wall: TypeWallDTO, restaurant: Restaurant) =
            SchemaItem(
                id = wall.id,
                x = wall.position.x,
                y = wall.position.y,
                width = wall.details.width,
                height = wall.details.height,
                rotation = wall.details.rotation,
                type = WALL,
                floor = restaurant.getFloor(wall.floorId)
            )
    }
}