package pl.denisolek.panel.employee.DTO

import pl.denisolek.core.security.Authority
import pl.denisolek.core.user.User

data class EmployeeDTO(
    var id: Int? = null,
    var firstName: String,
    var lastName: String,
    var email: String,
    var phoneNumber: String,
    var role: Authority.Role,
    var avatar: String? = null
) {
    companion object {
        fun fromUser(user: User, isOwner: Boolean = false) =
            EmployeeDTO(
                id = user.id,
                firstName = user.firstName!!,
                lastName = user.lastName!!,
                email = when {
                    isOwner -> user.email
                    else -> user.workEmail!!
                },
                phoneNumber = user.phoneNumber!!,
                role = when {
                    isOwner -> Authority.Role.ROLE_OWNER
                    else -> user.getRole()
                },
                avatar = user.avatar?.uuid
            )
    }
}