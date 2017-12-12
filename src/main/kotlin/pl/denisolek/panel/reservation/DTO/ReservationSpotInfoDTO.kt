package pl.denisolek.panel.reservation.DTO

import pl.denisolek.core.spot.Spot

data class ReservationSpotInfoDTO(
        var id: Int,
        var number: Int,
        var floorId: Int,
        var floorName: String
) {
    companion object {
        fun fromSpots(spots: List<Spot>) = spots.map { spot ->
            ReservationSpotInfoDTO(
                    id = spot.id!!,
                    number = spot.number,
                    floorId = spot.schemaItem?.floor?.id!!,
                    floorName = spot.schemaItem?.floor?.name ?: ""
            )
        }
    }
}