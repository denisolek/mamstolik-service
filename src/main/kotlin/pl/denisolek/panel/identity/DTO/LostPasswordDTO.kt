package pl.denisolek.panel.identity.DTO

import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.NotBlank

data class LostPasswordDTO(
        @field:Email
        @field:NotBlank
        var email: String
)