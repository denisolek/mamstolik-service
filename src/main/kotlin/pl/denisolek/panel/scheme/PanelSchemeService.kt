package pl.denisolek.panel.scheme

import org.springframework.stereotype.Service
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.panel.scheme.DTO.SchemeDTO

@Service
class PanelSchemeService {
    fun getScheme(restaurant: Restaurant): SchemeDTO {
        return SchemeDTO(restaurant)
    }
}