package pl.denisolek.panel.identity.DTO

import pl.denisolek.core.security.Authority
import pl.denisolek.core.user.User

data class RestaurantEmployeeDTO(
    var id: Int,
    var username: String,
    var firstName: String,
    var lastName: String,
    var role: Authority.Role,
    var avatar: String? = null
) {
    companion object {
        fun fromUser(user: User, isOwner: Boolean = false): RestaurantEmployeeDTO =
            RestaurantEmployeeDTO(
                id = user.id!!,
                username = user.username!!,
                firstName = user.firstName!!,
                lastName = user.lastName!!,
                role = when {
                    isOwner -> Authority.Role.ROLE_OWNER
                    else -> user.getRole()
                },
                avatar = user.avatar?.uuid
            )
    }
}