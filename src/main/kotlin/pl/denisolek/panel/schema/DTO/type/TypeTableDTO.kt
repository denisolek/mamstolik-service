package pl.denisolek.panel.schema.DTO.type

import org.springframework.http.HttpStatus
import pl.denisolek.Exception.ServiceException
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.schema.SchemaItem
import pl.denisolek.core.schema.SchemaItem.TableType
import pl.denisolek.core.schema.SchemaItem.Type.TABLE
import pl.denisolek.core.spot.Spot

data class TypeTableDTO(
        var id: Int? = null,
        var floorId: Int,
        var subType: TableType,
        var position: SchemaPositionDTO,
        var details: SchemaDetailsDTO,
        var spotInfo: SchemaSpotInfoDTO
) {
    constructor(item: SchemaItem) : this(
            id = item.id,
            floorId = item.floor.id!!,
            subType = item.tableType!!,
            details = SchemaDetailsDTO(
                    width = item.width,
                    heigth = item.height,
                    rotation = item.rotation
            ),
            position = SchemaPositionDTO(
                    x = item.x,
                    y = item.y
            ),
            spotInfo = SchemaSpotInfoDTO(
                    id = item.spot?.id,
                    number = item.spot!!.number,
                    capacity = item.spot!!.capacity,
                    minPeopleNumber = item.spot!!.minPeopleNumber
            )
    )

    companion object {
        fun toSchemaItem(table: TypeTableDTO, restaurant: Restaurant): SchemaItem {
            if (table.id == null && table.spotInfo.id != null)
                throw ServiceException(HttpStatus.BAD_REQUEST, "You can't assign exisiting spot to the new table.")
            return SchemaItem(
                    id = table.id,
                    x = table.position.x,
                    y = table.position.y,
                    width = table.details.width,
                    height = table.details.heigth,
                    rotation = table.details.rotation,
                    type = TABLE,
                    tableType = table.subType,
                    spot = Spot(
                            id = setProperSpotId(table, restaurant),
                            number = table.spotInfo.number,
                            capacity = table.spotInfo.capacity,
                            minPeopleNumber = table.spotInfo.minPeopleNumber,
                            restaurant = restaurant
                    ),
                    floor = restaurant.getFloor(table.floorId)
            )
        }

        private fun setProperSpotId(table: TypeTableDTO, restaurant: Restaurant): Int? {
            return when {
                table.id != null -> restaurant.floors.mapNotNull {
                    it.schemaItems.find { it.id == table.id }
                }.firstOrNull()?.spot?.id
                else -> null
            }
        }
    }
}