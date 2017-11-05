package pl.denisolek.shared.user

import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.GetMapping
import pl.denisolek.shared.user.dto.UserDTO

@Api("User controller", tags = arrayOf("User"))
interface UserApi {
    companion object {
        const val USERS_PATH = "/users"
    }

    @GetMapping(USERS_PATH)
    fun getUser(): UserDTO
}