package pl.denisolek.panel.schema.DTO.type

import pl.denisolek.core.schema.Floor
import pl.denisolek.core.schema.SchemaItem

data class SchemaFloorDTO(
        var id: Int? = null,
        var name: String,
        var tableCount: Int
) {
    constructor(floor: Floor) : this(
            id = floor.id,
            name = floor.name,
            tableCount = floor.schemaItems.filter { it.type == SchemaItem.Type.TABLE }.size
    )
}