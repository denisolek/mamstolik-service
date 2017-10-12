package pl.denisolek.identity.user

import org.springframework.stereotype.Service
import pl.denisolek.core.user.UserService
import pl.denisolek.identity.user.DTO.RegisterDTO

@Service
class IdentityUserService(private val userService: UserService) {
    fun registerOwner(registerDTO: RegisterDTO) {
        val username = userService.generateUsername()
        userService.save(registerDTO.toUser().copy(
                username = username
        ))
    }
}