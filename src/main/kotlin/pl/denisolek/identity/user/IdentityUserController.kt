package pl.denisolek.identity.user

import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pl.denisolek.identity.user.DTO.RegisterDTO
import javax.validation.Valid

@RestController
class IdentityUserController(val identityUserService: IdentityUserService) : IdentityUserApi {
    override fun registerOwner(@RequestBody @Valid registerDTO: RegisterDTO) {
        identityUserService.registerOwner(registerDTO)
    }
}