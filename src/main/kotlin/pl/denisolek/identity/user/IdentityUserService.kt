package pl.denisolek.identity.user

import org.springframework.stereotype.Service
import pl.denisolek.core.email.EmailService
import pl.denisolek.core.user.UserService
import pl.denisolek.identity.user.DTO.RegisterDTO

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
}