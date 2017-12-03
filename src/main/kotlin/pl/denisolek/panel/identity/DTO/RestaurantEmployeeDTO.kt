package pl.denisolek.panel.identity.DTO

import pl.denisolek.core.security.Authority
import pl.denisolek.core.user.User

data class RestaurantEmployeeDTO(
        var id: Int,
        var username: String,
        var firstName: String,
        var lastName: String,
        var title: Authority.Role,
        var avatar: String? = null
) {
    companion object {
        fun fromEmployees(employees: List<User>?): List<RestaurantEmployeeDTO> =
                employees?.map {
                    RestaurantEmployeeDTO(
                            id = it.id!!,
                            username = it.username!!,
                            firstName = it.firstName!!,
                            lastName = it.lastName!!,
                            title = it.getRole(),
                            avatar = it.avatar?.uuid
                    )
                } ?: listOf()

        fun getOwner(owner: User): RestaurantEmployeeDTO =
                RestaurantEmployeeDTO(
                        id = owner.id!!,
                        username = owner.username!!,
                        firstName = owner.firstName!!,
                        lastName = owner.lastName!!,
                        title = Authority.Role.ROLE_OWNER,
                        avatar = owner.avatar?.uuid
                )
    }
}