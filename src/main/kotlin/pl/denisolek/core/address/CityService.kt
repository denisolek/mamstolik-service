package pl.denisolek.core.address

import org.springframework.stereotype.Service

@Service
class CityService(private val cityRepository: CityRepository) {
    fun findPartlyByNameOrAlias(name: String): List<City> =
            cityRepository.findPartlyByNameOrAlias(name)

    fun findByNameIgnoreCase(name: String): City? =
            cityRepository.findByNameIgnoreCase(name)
}