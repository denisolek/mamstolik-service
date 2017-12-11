package pl.denisolek.panel.identity

import io.swagger.annotations.ApiImplicitParam
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.denisolek.panel.identity.DTO.*
import springfox.documentation.annotations.ApiIgnore
import javax.validation.Valid

@RestController
class IdentityController(val identityService: IdentityService) : IdentityApi {
    companion object {
        val API = IdentityApi.Companion
    }

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

    @ApiImplicitParam(name = API.URL_NAME, value = "Restaurant url name", paramType = "path", dataType = "string")
    override fun getRestaurant(@ApiIgnore @PathVariable(API.URL_NAME) urlName: String): RestaurantLoginDTO {
        return identityService.getRestaurant(urlName)
    }

    override fun getEmployees(): List<RestaurantEmployeeDTO> {
        return identityService.getEmployees()
    }

    override fun createRestaurant(@RequestBody @Valid createRestaurantDTO: CreateRestaurantDTO): UserRestaurantDTO {
        return identityService.createRestaurant(createRestaurantDTO)
    }
}