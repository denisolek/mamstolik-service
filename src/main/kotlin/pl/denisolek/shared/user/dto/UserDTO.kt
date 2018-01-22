package pl.denisolek.shared.user.dto

import pl.denisolek.core.security.Authority
import pl.denisolek.core.user.User

data class UserDTO(
    var id: Int,
    var username: String,
    var email: String,
    var firstName: String? = null,
    var lastName: String? = null,
    var avatar: String? = null,
    var accountState: User.AccountState,
    var authorities: Set<Authority.Role>
) {
    companion object {
        fun fromUser(user: User) =
            UserDTO(
                id = user.id!!,
                username = user.username!!,
                email = user.email,
                firstName = user.firstName!!,
                lastName = user.lastName!!,
                avatar = user.avatar?.uuid,
                accountState = user.accountState,
                authorities = user.authorities.map { it.role }.toSet()
            )
    }
}