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
    var uuid: String? = null,
    var floorId: Int,
    var subType: TableType? = null,
    var position: SchemaPositionDTO? = null,
    var details: SchemaDetailsDTO? = null,
    var spotInfo: SchemaSpotInfoDTO
) {
    constructor(item: SchemaItem) : this(
        id = item.id,
        uuid = item.uuid,
        floorId = item.floor.id!!,
        subType = item.tableType!!,
        details = SchemaDetailsDTO(
            width = item.width,
            height = item.height,
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
        private const val DEFAULT_X = 300f
        private const val DEFAULT_Y = 300f
        private const val DEFAULT_WIDTH = 200f
        private const val DEFAULT_HEIGHT = 200f
        private const val DEFAULT_ROTATION = 0f

        fun toSchemaItem(table: TypeTableDTO, restaurant: Restaurant): SchemaItem {
            if ((table.id == null || !restaurantContainsTable(table, restaurant)) && restaurantContainsSpot(
                    table,
                    restaurant
                )
            )
                throw ServiceException(HttpStatus.BAD_REQUEST, "You can't assign exisiting spot to the new table.")
            return SchemaItem(
                id = if (restaurantContainsTable(table, restaurant)) table.id else null,
                uuid = table.uuid,
                x = table.position?.x ?: DEFAULT_X,
                y = table.position?.y ?: DEFAULT_Y,
                width = table.details?.width ?: DEFAULT_WIDTH,
                height = table.details?.height ?: DEFAULT_HEIGHT,
                rotation = table.details?.rotation ?: DEFAULT_ROTATION,
                type = TABLE,
                tableType = setTableType(table),
                spot = setSpot(table, restaurant),
                floor = restaurant.getFloor(table.floorId)
            )
        }

        private fun setSpot(table: TypeTableDTO, restaurant: Restaurant): Spot {
            if (table.spotInfo.capacity !in 1..100 || table.spotInfo.minPeopleNumber !in 1..100)
                throw ServiceException(HttpStatus.BAD_REQUEST, "Capacity and minPeopleNumber must be in range 1-100")
            return Spot(
                id = setProperSpotId(table, restaurant),
                number = table.spotInfo.number,
                capacity = table.spotInfo.capacity,
                minPeopleNumber = table.spotInfo.minPeopleNumber,
                restaurant = restaurant
            )
        }

        private fun setTableType(table: TypeTableDTO): TableType? {
            return table.subType ?: when (table.spotInfo.capacity) {
                in 0..2 -> TableType.TWO
                3 -> TableType.THREE
                4 -> TableType.FOUR
                5 -> TableType.FIVE_ROUND
                else -> TableType.EIGHT_ROUND
            }
        }

        private fun setProperSpotId(table: TypeTableDTO, restaurant: Restaurant): Int? {
            return when {
                table.id != null && restaurantContainsTable(table, restaurant) -> restaurant.floors.mapNotNull {
                    it.schemaItems.find { it.id == table.id }
                }.firstOrNull()?.spot?.id
                else -> null
            }
        }

        private fun restaurantContainsTable(table: TypeTableDTO, restaurant: Restaurant): Boolean {
            restaurant.floors.forEach {
                if (it.schemaItems.filter { it.id == table.id }.count() > 0)
                    return true
            }
            return false
        }

        private fun restaurantContainsSpot(table: TypeTableDTO, restaurant: Restaurant): Boolean {
            when {
                table.spotInfo.id != null -> restaurant.floors.forEach {
                    if (it.schemaItems.filter { it.spot?.id == table.spotInfo.id }.count() > 0)
                        return true
                }
            }
            return false
        }
    }
}