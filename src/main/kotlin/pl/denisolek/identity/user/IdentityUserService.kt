package pl.denisolek.identity.user

import pl.denisolek.core.user.UserService
import pl.denisolek.identity.user.DTO.RegisterDTO

class IdentityUserService(private val userService: UserService) {
    fun registerOwner(registerDTO: RegisterDTO) {

        userService.save(registerDTO.toUser())
    }
}