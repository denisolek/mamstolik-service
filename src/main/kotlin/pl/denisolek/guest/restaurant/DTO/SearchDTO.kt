package pl.denisolek.guest.restaurant.DTO

data class SearchDTO(
        val available: MutableList<RestaurantDTO>,
        val possible: MutableList<RestaurantDTO>,
        val notAvailable: MutableList<RestaurantDTO>,
        val closed: MutableList<RestaurantDTO>
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