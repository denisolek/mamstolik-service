package pl.denisolek.panel.schema.DTO.type

import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.schema.SchemaItem
import pl.denisolek.core.schema.SchemaItem.Type.ITEM

data class TypeItemDTO(
        var id: Int? = null,
        var floorId: Int,
        var subType: SchemaItem.ItemType,
        var position: SchemaPositionDTO,
        var details: SchemaDetailsDTO
) {
    constructor(item: SchemaItem) : this(
            id = item.id,
            floorId = item.floor.id!!,
            subType = item.itemType!!,
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

    companion object {
        fun toSchemaItem(item: TypeItemDTO, restaurant: Restaurant) =
                SchemaItem(
                        id = item.id,
                        x = item.position.x,
                        y = item.position.y,
                        width = item.details.width,
                        height = item.details.heigth,
                        rotation = item.details.rotation,
                        type = ITEM,
                        itemType = item.subType,
                        floor = restaurant.getFloor(item.floorId)
                )
    }
}