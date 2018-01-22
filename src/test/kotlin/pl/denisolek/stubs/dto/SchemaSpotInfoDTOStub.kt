package pl.denisolek.stubs.dto

import pl.denisolek.panel.schema.DTO.type.SchemaSpotInfoDTO

class SchemaSpotInfoDTOStub {
    companion object {
        fun getSchemaSpotInfoDTOStub() =
            SchemaSpotInfoDTO(
                number = 500,
                capacity = 5,
                minPeopleNumber = 2
            )
    }
}