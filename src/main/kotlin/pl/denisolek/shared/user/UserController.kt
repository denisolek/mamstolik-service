package pl.denisolek.shared.user

import org.springframework.web.bind.annotation.RestController
import pl.denisolek.infrastructure.config.security.AuthorizationService
import pl.denisolek.shared.user.dto.UserDTO

@RestController
class UserController(private val authorizationService: AuthorizationService) : UserApi {
    override fun getUser(): UserDTO =
            UserDTO.fromUser(authorizationService.getCurrentUser())
}