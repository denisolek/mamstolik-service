package pl.denisolek.panel.schema

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import pl.denisolek.Exception.ServiceException
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.restaurant.RestaurantService
import pl.denisolek.core.schema.Floor
import pl.denisolek.core.schema.SchemaItem
import pl.denisolek.core.schema.SchemaService
import pl.denisolek.panel.schema.DTO.FloorDTO
import pl.denisolek.panel.schema.DTO.SchemaDTO

@Service
class PanelSchemaService(val restaurantService: RestaurantService,
                         val schemaService: SchemaService) {
    fun getSchema(restaurant: Restaurant): SchemaDTO {
        return SchemaDTO(restaurant)
    }

    fun addFloor(restaurant: Restaurant, floorDTO: FloorDTO): SchemaDTO {
        restaurant.floors.add(Floor(
                name = floorDTO.name,
                restaurant = restaurant
        ))
        return SchemaDTO(restaurantService.save(restaurant))
    }

    fun deleteFloor(restaurant: Restaurant, floor: Floor): SchemaDTO {
        if (floor.haveReservationsInFuture()) throw ServiceException(HttpStatus.CONFLICT, "There are some reservations including spots on that floor")
        restaurant.floors.remove(floor)
        return SchemaDTO(restaurantService.save(restaurant))
    }

    // TODO usuwanie nie działa
    // TODO przemyśleć proces czyszczenia kolekcji,inaczej nie ma prawa działać
//    fun updateSchema(restaurant: Restaurant, schemaDTO: SchemaDTO): SchemaDTO {
//        val items = SchemaDTO.toSchemaItems(schemaDTO, restaurant)
//        val updatedItems = schemaService.saveSchemaItems(items)
//        val groupedItems = updatedItems.groupBy { it.floor.id }
//        updateRestaurantSpots(updatedItems, restaurant)
//        updateRestaurantFloors(restaurant, groupedItems)
//        return SchemaDTO(restaurant)
//    }
//
//    private fun updateRestaurantFloors(restaurant: Restaurant, groupedItems: Map<Int?, List<SchemaItem>>) {
//        restaurant.floors.forEach { (id, _, schemaItems) ->
//            schemaItems.clear()
//            if (groupedItems[id] != null)
//                schemaItems.addAll(groupedItems[id]!!.toMutableList())
//        }
//    }
//
//    private fun updateRestaurantSpots(updatedItems: MutableList<SchemaItem>, restaurant: Restaurant) {
//        updatedItems.forEach {
//            if (!restaurant.spots.contains(it.spot) && it.spot != null)
//                restaurant.spots.add(it.spot!!)
//        }
//    }

    fun updateSchema(restaurant: Restaurant, schemaDTO: SchemaDTO): SchemaDTO {
        val items = SchemaDTO.toSchemaItems(schemaDTO, restaurant)
                .groupBy { it.floor.id }

        restaurant.floors.map { (id, _, schemaItems) ->
            schemaItems.removeIf { it.type != SchemaItem.Type.TABLE }
            items[id]?.forEach { schemaItem ->
                when {
                    schemaItem.type == SchemaItem.Type.TABLE -> {
                        if (schemaItems.filter { it.id == schemaItem.id }.count() > 0) {
                            schemaItems.first { schemaItem.id == it.id }.let {
                                it.width = schemaItem.width
                                it.height = schemaItem.height
                                it.rotation = schemaItem.rotation
                                it.x = schemaItem.x
                                it.y = schemaItem.y
                            }
                        } else {
                            schemaItems.add(schemaItem)
                        }
                    }
                    else -> schemaItems.add(schemaItem)
                }
            }
        }
        return SchemaDTO(restaurantService.save(restaurant))
    }
}