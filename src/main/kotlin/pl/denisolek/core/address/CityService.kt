package pl.denisolek.core.address

import org.springframework.stereotype.Service

@Service
class CityService(private val cityRepository: CityRepository) {
    fun getCities(name: String): List<City> =
            cityRepository.findAll()
}