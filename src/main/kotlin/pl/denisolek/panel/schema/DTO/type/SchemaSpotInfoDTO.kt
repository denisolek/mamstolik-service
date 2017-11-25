package pl.denisolek.panel.schema.DTO.type

data class SchemaSpotInfoDTO(
        var id: Int? = null,
        var number: Int,
        var capacity: Int,
        var minPeopleNumber: Int = 1
)