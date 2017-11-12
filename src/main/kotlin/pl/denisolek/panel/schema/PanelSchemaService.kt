package pl.denisolek.panel.schema

import org.springframework.stereotype.Service
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.restaurant.RestaurantService
import pl.denisolek.core.schema.Floor
import pl.denisolek.panel.schema.DTO.FloorDTO
import pl.denisolek.panel.schema.DTO.SchemaDTO

@Service
class PanelSchemaService(val restaurantService: RestaurantService) {
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
        floor.haveReservationsInFuture()
        return SchemaDTO(restaurant)
    }
}