package pl.denisolek.panel.identity

import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.denisolek.panel.identity.DTO.ChangePasswordDTO
import pl.denisolek.panel.identity.DTO.RegisterDTO
import pl.denisolek.panel.identity.DTO.SetPasswordDTO
import pl.denisolek.panel.identity.DTO.UserRestaurantDTO
import javax.validation.Valid

@RestController
class IdentityController(val identityService: IdentityService) : IdentityApi {
    override fun registerOwner(@RequestBody @Valid registerDTO: RegisterDTO) {
        identityService.registerOwner(registerDTO)
    }

    override fun resendActivationKey(@RequestParam(required = true) email: String) {
        identityService.resendActivationKey(email)
    }

    override fun setPassword(@RequestBody @Valid setPasswordDTO: SetPasswordDTO) {
        identityService.setPassword(setPasswordDTO)
    }

    override fun changePassword(@RequestBody @Valid changePasswordDTO: ChangePasswordDTO) {
        identityService.changePassword(changePasswordDTO)
    }

    override fun getRestaurants(): List<UserRestaurantDTO> {
        return identityService.getRestaurants()
    }
}