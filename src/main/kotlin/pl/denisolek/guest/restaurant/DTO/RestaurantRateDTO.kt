package pl.denisolek.guest.restaurant.DTO

data class RestaurantRateDTO(
    var count: Int,
    var total: Float,
    var service_rate: Float,
    var food_rate: Float,
    var price_quality_rate: Float
)
