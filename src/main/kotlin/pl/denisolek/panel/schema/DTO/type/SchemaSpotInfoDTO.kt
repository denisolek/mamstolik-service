package pl.denisolek.panel.schema.DTO.type

import javax.validation.constraints.Min

data class SchemaSpotInfoDTO(
        var id: Int? = null,
        var number: Int,
        @field:Min(0)
        var capacity: Int,
        @field:Min(1)
        var minPeopleNumber: Int = 1
)