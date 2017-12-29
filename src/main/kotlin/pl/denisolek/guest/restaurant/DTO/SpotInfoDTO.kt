package pl.denisolek.guest.restaurant.DTO

data class SpotInfoDTO(
        var id: Int,
        var state: SpotState
) {
    enum class SpotState {
        TAKEN,
        POSSIBLE,
        AVAILABLE,
        NOT_AVAILABLE
    }
}