package pl.denisolek.panel.identity.DTO

import pl.denisolek.core.user.User

data class RestaurantEmployeeDTO(
        var id: Int,
        var username: String,
        var fullName: String,
        var title: String,
        var avatar: String
) {
    companion object {
        fun fromEmployees(employees: List<User>?): List<RestaurantEmployeeDTO>? =
                employees?.map {
                    RestaurantEmployeeDTO(
                            id = it.id!!,
                            username = it.username!!,
                            fullName = "${it.firstName} ${it.lastName}",
                            title = it.getTitle(),
                            avatar = "avatar link"
                    )
                }
    }
}