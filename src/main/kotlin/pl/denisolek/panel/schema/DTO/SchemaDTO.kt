package pl.denisolek.panel.schema.DTO

import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.schema.SchemaItem
import pl.denisolek.panel.schema.DTO.type.TypeItemDTO
import pl.denisolek.panel.schema.DTO.type.TypeTableDTO
import pl.denisolek.panel.schema.DTO.type.TypeWallDTO
import pl.denisolek.panel.schema.DTO.type.TypeWallItemDTO

data class SchemaDTO(
        var tables: List<TypeTableDTO>,
        var walls: List<TypeWallDTO>,
        var items: List<TypeItemDTO>,
        var wallItems: List<TypeWallItemDTO>
) {
    constructor(restaurant: Restaurant) : this(
            tables = restaurant.floors.flatMap { it.schemaItems.filter { it.type == SchemaItem.Type.TABLE }.map { TypeTableDTO(it) } },
            walls = restaurant.floors.flatMap { it.schemaItems.filter { it.type == SchemaItem.Type.WALL }.map { TypeWallDTO(it) } },
            items = restaurant.floors.flatMap { it.schemaItems.filter { it.type == SchemaItem.Type.ITEM }.map { TypeItemDTO(it) } },
            wallItems = restaurant.floors.flatMap { it.schemaItems.filter { it.type == SchemaItem.Type.WALL_ITEM }.map { TypeWallItemDTO(it) } }
    )
}