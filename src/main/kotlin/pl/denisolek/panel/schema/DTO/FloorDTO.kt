package pl.denisolek.panel.schema.DTO

import javax.validation.constraints.Size

data class FloorDTO(
        @field:Size(max = 100, message = "Name too long.")
        var name: String
)