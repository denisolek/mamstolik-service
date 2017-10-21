package pl.denisolek.identity.user

import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.denisolek.identity.user.DTO.ChangePasswordDTO
import pl.denisolek.identity.user.DTO.RegisterDTO
import pl.denisolek.identity.user.DTO.SetPasswordDTO
import javax.validation.Valid

@RestController
class IdentityUserController(val identityUserService: IdentityUserService) : IdentityUserApi {
    override fun registerOwner(@RequestBody @Valid registerDTO: RegisterDTO) {
        identityUserService.registerOwner(registerDTO)
    }

    override fun resendActivationKey(@RequestParam(required = true) email: String) {
        identityUserService.resendActivationKey(email)
    }

    override fun setPassword(@RequestBody @Valid setPasswordDTO: SetPasswordDTO) {
        identityUserService.setPassword(setPasswordDTO)
    }

    override fun changePassword(@RequestBody @Valid changePasswordDTO: ChangePasswordDTO) {
        identityUserService.changePassword(changePasswordDTO)
    }
}