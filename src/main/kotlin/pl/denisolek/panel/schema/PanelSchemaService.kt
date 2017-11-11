package pl.denisolek.panel.schema

import org.springframework.stereotype.Service
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.panel.schema.DTO.SchemaDTO

@Service
class PanelSchemaService {
    fun getSchema(restaurant: Restaurant): SchemaDTO {
        return SchemaDTO(restaurant)
    }
}