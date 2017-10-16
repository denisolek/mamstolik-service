package pl.denisolek.identity.user

import io.swagger.annotations.Api
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import pl.denisolek.identity.user.DTO.RegisterDTO
import pl.denisolek.identity.user.DTO.SetPasswordDTO
import pl.denisolek.infrastructure.IDENTITY_BASE_PATH
import javax.validation.Valid

@Api("User controller", tags = arrayOf("User"))
@RequestMapping(IDENTITY_BASE_PATH)
interface IdentityUserApi {
    companion object {
        const val USERS_BASE_PATH = "/users"
        const val USERS_PASSWORD_PATH = "$USERS_BASE_PATH/password"
        const val USERS_RESEND_ACTIVATION_KEY_PATH = "$USERS_BASE_PATH/resend-activation"
        const val USERS_LOST_PASSWORD_PATH = "$USERS_BASE_PATH/lost-password"
        const val USERS_RESET_PASSWORD_PATH = "$USERS_BASE_PATH/reset-password"
        const val RESTAURANTS_BASE_PATH = "/restaurants"
        const val EMPLOYEES_BASE_PATH = "/employees"
    }

    @PostMapping(USERS_BASE_PATH)
    @ResponseStatus(HttpStatus.CREATED)
    fun registerOwner(@RequestBody @Valid registerDTO: RegisterDTO)

    @PostMapping(USERS_RESEND_ACTIVATION_KEY_PATH)
    fun resendActivationKey(@RequestParam(required = true) email: String)

    @PostMapping(USERS_PASSWORD_PATH)
    fun setPassword(@RequestBody @Valid setPasswordDTO: SetPasswordDTO)
}