package pl.denisolek.panel.identity

import io.swagger.annotations.Api
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import pl.denisolek.infrastructure.PANEL_BASE_PATH
import pl.denisolek.panel.identity.DTO.*
import javax.validation.Valid

@Api("Identity controller", tags = arrayOf("Identity"))
@RequestMapping(PANEL_BASE_PATH)
interface IdentityApi {
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

    @PutMapping(USERS_PASSWORD_PATH)
    fun changePassword(@RequestBody @Valid changePasswordDTO: ChangePasswordDTO)

    @PostMapping(USERS_LOST_PASSWORD_PATH)
    fun lostPassword(@RequestBody @Valid lostPasswordDTO: LostPasswordDTO)

    @PutMapping(USERS_RESET_PASSWORD_PATH)
    fun resetPassword(@RequestBody @Valid resetPasswordDTO: ResetPasswordDTO)

    @GetMapping(RESTAURANTS_BASE_PATH)
    fun getRestaurants(): List<UserRestaurantDTO>

    @GetMapping(EMPLOYEES_BASE_PATH)
    fun getEmployees(): List<RestaurantEmployeeDTO>
}