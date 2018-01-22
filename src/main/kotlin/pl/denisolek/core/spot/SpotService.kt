package pl.denisolek.core.spot

import org.springframework.stereotype.Service

@Service
class SpotService(private val spotRepository: SpotRepository) {
    fun save(spot: Spot) =
        spotRepository.save(spot)
}