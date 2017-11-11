package pl.denisolek.panel.scheme.DTO

import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.scheme.SchemeItem
import pl.denisolek.panel.scheme.DTO.type.TypeItemDTO
import pl.denisolek.panel.scheme.DTO.type.TypeTableDTO
import pl.denisolek.panel.scheme.DTO.type.TypeWallDTO
import pl.denisolek.panel.scheme.DTO.type.TypeWallItemDTO

data class SchemeDTO(
        var tables: List<TypeTableDTO>,
        var walls: List<TypeWallDTO>,
        var items: List<TypeItemDTO>,
        var wallItems: List<TypeWallItemDTO>
) {
    constructor(restaurant: Restaurant) : this(
            tables = restaurant.floors.flatMap { it.schemeItems.filter { it.type == SchemeItem.Type.TABLE }.map { TypeTableDTO(it) } },
            walls = restaurant.floors.flatMap { it.schemeItems.filter { it.type == SchemeItem.Type.WALL }.map { TypeWallDTO(it) } },
            items = restaurant.floors.flatMap { it.schemeItems.filter { it.type == SchemeItem.Type.ITEM }.map { TypeItemDTO(it) } },
            wallItems = restaurant.floors.flatMap { it.schemeItems.filter { it.type == SchemeItem.Type.WALL_ITEM }.map { TypeWallItemDTO(it) } }
    )
}