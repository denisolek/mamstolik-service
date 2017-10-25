package pl.denisolek.panel.user

import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import pl.denisolek.Exception.ServiceException
import pl.denisolek.core.email.EmailService
import pl.denisolek.core.user.User
import pl.denisolek.core.user.UserService
import pl.denisolek.infrastructure.config.security.AuthorizationService
import pl.denisolek.panel.user.DTO.ChangePasswordDTO
import pl.denisolek.panel.user.DTO.RegisterDTO
import pl.denisolek.panel.user.DTO.SetPasswordDTO
import pl.denisolek.panel.user.DTO.UserRestaurantDTO

@Service
class PanelUserService(private val userService: UserService,
                       private val emailService: EmailService,
                       private val authorizationService: AuthorizationService,
                       private val passwordEncoder: PasswordEncoder) {
    fun registerOwner(registerDTO: RegisterDTO) {
        val username = userService.generateUsername()
        val newUser = userService.save(registerDTO.toUser().copy(
                username = username
        ))
        Thread { emailService.registerOwner(newUser) }.start()
    }

    fun resendActivationKey(email: String) {
        val user: User? = userService.findByEmail(email)

        if (user?.activationKey != null) Thread { emailService.registerOwner(user) }.start()
    }

    fun setPassword(setPasswordDTO: SetPasswordDTO) {
        val user = userService.findByUsername(setPasswordDTO.username) ?: throw ServiceException(HttpStatus.NOT_FOUND, "User not found.")

        when {
            user.activationKey == setPasswordDTO.activationKey -> {
                user.password = passwordEncoder.encode(setPasswordDTO.password)
                user.activationKey = null
                user.accountState = User.AccountState.ACTIVE
                userService.save(user)
            }
            else -> throw ServiceException(HttpStatus.BAD_REQUEST, "Activation key doesn't match or password is already set.")
        }
    }

    fun changePassword(changePasswordDTO: ChangePasswordDTO) {
        val user = authorizationService.getCurrentUser()
        if (passwordEncoder.matches(changePasswordDTO.oldPassword, user.password))
            user.password = passwordEncoder.encode(changePasswordDTO.newPassword)
        else
            throw ServiceException(HttpStatus.BAD_REQUEST, "Old password doesn't match")
        userService.save(user)
    }

    fun getRestaurants(): List<UserRestaurantDTO> {
        val user = authorizationService.getCurrentUser()
        return user.ownedRestaurants?.map {
            UserRestaurantDTO.fromRestaurant(it)
        } ?: mutableListOf()
    }
}