package pl.denisolek.panel.identity.DTO

import org.hibernate.validator.constraints.NotBlank
import javax.validation.constraints.Pattern

data class ResetPasswordDTO(
    @field:NotBlank
    var username: String,

    @field:NotBlank
    @field:Pattern(
        regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,50})",
        message = "Password requires 8-50 length and at least one of each: a-z, A-Z, 1-9"
    )
    var password: String,

    @field:NotBlank
    var resetKey: String
)