package pl.denisolek.panel.schema.DTO

import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.schema.SchemaItem
import pl.denisolek.panel.schema.DTO.type.*

data class SchemaDTO(
    var isGridEnabled: Boolean,
    var floors: List<SchemaFloorDTO>? = null,
    var tables: MutableList<TypeTableDTO>,
    var walls: MutableList<TypeWallDTO>,
    var items: MutableList<TypeItemDTO>,
    var wallItems: MutableList<TypeWallItemDTO>
) {
    constructor(restaurant: Restaurant) : this(
        isGridEnabled = restaurant.settings.schema,
        floors = restaurant.floors.map { SchemaFloorDTO(it) },
        tables = restaurant.floors.flatMap {
            it.schemaItems.filter { it.type == SchemaItem.Type.TABLE }.map { TypeTableDTO(it) }
        }.toMutableList(),
        walls = restaurant.floors.flatMap {
            it.schemaItems.filter { it.type == SchemaItem.Type.WALL }.map { TypeWallDTO(it) }
        }.toMutableList(),
        items = restaurant.floors.flatMap {
            it.schemaItems.filter { it.type == SchemaItem.Type.ITEM }.map { TypeItemDTO(it) }
        }.toMutableList(),
        wallItems = restaurant.floors.flatMap {
            it.schemaItems.filter { it.type == SchemaItem.Type.WALL_ITEM }.map { TypeWallItemDTO(it) }
        }.toMutableList()
    )

    companion object {
        fun toSchemaItems(schemaDTO: SchemaDTO, restaurant: Restaurant) =
            schemaDTO.tables.map { TypeTableDTO.toSchemaItem(it, restaurant) }
                .plus(schemaDTO.walls.map { TypeWallDTO.toSchemaItem(it, restaurant) })
                .plus(schemaDTO.items.map { TypeItemDTO.toSchemaItem(it, restaurant) })
                .plus(schemaDTO.wallItems.map { TypeWallItemDTO.toSchemaItem(it, restaurant) })
    }
}