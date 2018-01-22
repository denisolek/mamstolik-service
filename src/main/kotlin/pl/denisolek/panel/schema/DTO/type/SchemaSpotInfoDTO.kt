package pl.denisolek.panel.schema.DTO.type

import pl.denisolek.core.spot.Spot
import javax.validation.constraints.Max
import javax.validation.constraints.Min

data class SchemaSpotInfoDTO(
    var id: Int? = null,
    var number: Int,
    @field:Min(1)
    @field:Max(100)
    var capacity: Int,
    @field:Min(1)
    @field:Max(100)
    var minPeopleNumber: Int = 1
) {
    companion object {
        fun fromSpot(spot: Spot) = SchemaSpotInfoDTO(
            id = spot.id,
            number = spot.number,
            capacity = spot.capacity,
            minPeopleNumber = spot.minPeopleNumber
        )
    }
}