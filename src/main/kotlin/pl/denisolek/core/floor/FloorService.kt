package pl.denisolek.core.floor

import org.springframework.stereotype.Service

@Service
class FloorService(private val floorRepository: FloorRepository) {
    fun save(floor: Floor) =
        floorRepository.save(floor)
}