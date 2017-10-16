package pl.denisolek.identity.user

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import pl.denisolek.Exception.ServiceException
import pl.denisolek.core.email.EmailService
import pl.denisolek.core.user.User
import pl.denisolek.core.user.UserService
import pl.denisolek.identity.user.DTO.RegisterDTO
import pl.denisolek.identity.user.DTO.SetPasswordDTO

@Service
class IdentityUserService(private val userService: UserService,
                          private val emailService: EmailService) {
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
        val user = userService.findByUsername(setPasswordDTO.username) ?: throw ServiceException(HttpStatus.NOT_FOUND, "User not found")

        when {
            user.activationKey == setPasswordDTO.activationKey -> {
                user.password = setPasswordDTO.password
                user.activationKey = null
                user.accountState = User.AccountState.ACTIVE
                userService.save(user)
            }
            else -> throw ServiceException(HttpStatus.BAD_REQUEST, "Activation key doesn't match or password is already set")
        }
    }
}