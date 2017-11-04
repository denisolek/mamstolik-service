package pl.denisolek.panel.identity

import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.denisolek.panel.identity.DTO.*
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

    override fun lostPassword(@RequestBody @Valid lostPasswordDTO: LostPasswordDTO) {
        identityService.lostPassword(lostPasswordDTO)
    }

    override fun resetPassword(@RequestBody @Valid resetPasswordDTO: ResetPasswordDTO) {
        identityService.resetPassword(resetPasswordDTO)
    }

    override fun getRestaurants(): List<UserRestaurantDTO> {
        return identityService.getRestaurants()
    }

    override fun getEmployees(): List<RestaurantEmployeeDTO> {
        return identityService.getEmployees()
    }
}