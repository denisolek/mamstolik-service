package pl.denisolek.guest.restaurant.DTO

data class SearchDTO(
    val available: MutableList<RestaurantSearchDTO>,
    val possible: MutableList<RestaurantSearchDTO>,
    val notAvailable: MutableList<RestaurantSearchDTO>,
    val closed: MutableList<RestaurantSearchDTO>
) {
    companion object {
        fun initSearchDTO(): SearchDTO = SearchDTO(
            available = mutableListOf(),
            possible = mutableListOf(),
            notAvailable = mutableListOf(),
            closed = mutableListOf()
        )
    }
}