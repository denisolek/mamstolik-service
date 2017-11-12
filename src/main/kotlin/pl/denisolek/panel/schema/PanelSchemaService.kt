package pl.denisolek.panel.schema

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import pl.denisolek.Exception.ServiceException
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
        if (floor.haveReservationsInFuture()) throw ServiceException(HttpStatus.CONFLICT, "There are some reservations including spots on that floor")
        restaurant.floors.remove(floor)
        return SchemaDTO(restaurantService.save(restaurant))
    }

    fun updateSchema(restaurant: Restaurant, schemaDTO: SchemaDTO): SchemaDTO {


        return schemaDTO
    }
}