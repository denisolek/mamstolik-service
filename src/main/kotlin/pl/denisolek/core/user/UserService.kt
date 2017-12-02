package pl.denisolek.core.user

import org.springframework.stereotype.Service
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.infrastructure.util.generateUsernameString

@Service
class UserService(val userRepository: UserRepository) {
    fun save(user: User) =
            userRepository.save(user)

    fun findByEmail(email: String): User? =
            userRepository.findByEmail(email)

    fun findByUsername(username: String): User? =
            userRepository.findByUsername(username)

    fun findByRestaurant(restaurant: Restaurant): User? =
            userRepository.findByRestaurant(restaurant)

    fun generateUsername(): String {
        var exists: Boolean = true
        var username: String = "username"

        while (exists) {
            username = generateUsernameString()
            if (userRepository.countByUsername(username) == 0)
                exists = false
        }
        return username
    }
}