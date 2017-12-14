package pl.denisolek.panel.customer.DTO

data class CustomerInfoDTO(
        var id: Int,
        var firstName: String,
        var lastName: String,
        var phoneNumber: String,
        var email: String,
        var isVip: Boolean,
        var comments: List<CommentDTO>,
        var reservations: CustomerReservationsDTO
)