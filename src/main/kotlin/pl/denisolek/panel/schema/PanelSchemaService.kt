package pl.denisolek.panel.schema

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import pl.denisolek.Exception.ServiceException
import pl.denisolek.core.floor.Floor
import pl.denisolek.core.floor.FloorService
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.restaurant.RestaurantService
import pl.denisolek.core.schema.SchemaItem
import pl.denisolek.core.spot.Spot
import pl.denisolek.core.spot.SpotService
import pl.denisolek.panel.schema.DTO.FloorDTO
import pl.denisolek.panel.schema.DTO.SchemaDTO
import pl.denisolek.panel.schema.DTO.type.SchemaSpotInfoDTO

@Service
class PanelSchemaService(val restaurantService: RestaurantService,
                         val floorService: FloorService,
                         val spotService: SpotService) {
    fun getSchema(restaurant: Restaurant): SchemaDTO {
        return SchemaDTO(restaurant)
    }

    fun addFloor(restaurant: Restaurant, floorDTO: FloorDTO): FloorDTO {
        val floor = floorService.save(Floor(
                name = floorDTO.name,
                restaurant = restaurant
        ))
        return FloorDTO(
                id = floor.id,
                name = floor.name
        )
    }

    fun deleteFloor(restaurant: Restaurant, floor: Floor) {
        if (floor.haveReservationsInFuture()) throw ServiceException(HttpStatus.CONFLICT, "There are some reservations including spots on that floor")
        restaurant.floors.remove(floor)
        restaurantService.save(restaurant)
    }

    fun updateSchema(restaurant: Restaurant, schemaDTO: SchemaDTO): SchemaDTO {
        val items = SchemaDTO.toSchemaItems(schemaDTO, restaurant)
        val restaurantTables = restaurant.floors
                .flatMap { it.schemaItems }
                .filter { it.type == SchemaItem.Type.TABLE }
                .toMutableList()
        val updatedItems = getUpdatedItems(items, restaurantTables)
        assignItemsToRestaurant(restaurant, updatedItems)
        restaurant.settings!!.schema = schemaDTO.isGridEnabled
        return SchemaDTO(restaurantService.save(restaurant))
    }

    fun updateSpot(restaurant: Restaurant, spot: Spot, spotInfoDTO: SchemaSpotInfoDTO): SchemaSpotInfoDTO {
        if (spot.haveReservationsInFuture())
            throw ServiceException(HttpStatus.CONFLICT, "This spot have reservations in future")

        val updatedSpot = restaurant.spots.find { it.id == spot.id } ?: throw ServiceException(HttpStatus.NOT_FOUND, "Spot not found in this restaurant")
        updatedSpot.number = spotInfoDTO.number
        updatedSpot.capacity = spotInfoDTO.capacity
        updatedSpot.minPeopleNumber = spotInfoDTO.minPeopleNumber

        return SchemaSpotInfoDTO.fromSpot(spotService.save(updatedSpot))
    }

    fun deleteSpot(restaurant: Restaurant, spot: Spot) {
        if (spot.haveReservationsInFuture())
            throw ServiceException(HttpStatus.CONFLICT, "This spot have reservations in future")

        if (spot.restaurant.id != restaurant.id)
            throw ServiceException(HttpStatus.NOT_FOUND, "Spot not found in this restaurant")

        restaurant.spots.removeIf { it.id == spot.id }
        restaurant.floors.map {
            it.schemaItems.removeIf { it.spot?.id == spot.id }
        }
        restaurantService.save(restaurant)
    }

    private fun getUpdatedItems(items: List<SchemaItem>, restaurantTables: MutableList<SchemaItem>): Map<Int?, List<SchemaItem?>> {
        return items.map { item ->
            when {
                (isExistingTable(item, restaurantTables)) -> restaurantTables.find { it.id == item.id }?.let {
                    updateExistingTable(it, item)
                    restaurantTables.removeIf { it.id == item.id }
                    it
                }
                else -> item
            }
        }.plus(restaurantTables).groupBy { it?.floor?.id }
    }

    private fun assignItemsToRestaurant(restaurant: Restaurant, updatedItems: Map<Int?, List<SchemaItem?>>) {
        restaurant.floors.forEach { (id, _, schemaItems) ->
            schemaItems.clear()
            when {
                updatedItems.containsKey(id) -> updatedItems[id]?.map { item ->
                    item?.let { schemaItems.add(it) }
                }
            }
        }
    }

    private fun updateExistingTable(it: SchemaItem, item: SchemaItem) {
        it.width = item.width
        it.height = item.height
        it.rotation = item.rotation
        it.x = item.x
        it.y = item.y
        it.tableType = item.tableType
        it.floor = item.floor
    }

    private fun isExistingTable(item: SchemaItem, restaurantTables: MutableList<SchemaItem>) =
            item.type == SchemaItem.Type.TABLE && restaurantTables.any { it.id == item.id }
}