package pl.denisolek.panel.customer.DTO

import pl.denisolek.core.comment.Comment
import pl.denisolek.core.customer.Customer
import pl.denisolek.core.reservation.Reservation

data class CustomerInfoDTO(
    var id: Int,
    var firstName: String,
    var lastName: String,
    var phoneNumber: String,
    var email: String,
    var isVip: Boolean,
    var comments: List<CommentDTO>,
    var reservations: CustomerReservationsDTO
) {
    companion object {
        fun getCustomerInfoDTO(
            customer: Customer,
            comments: List<Comment>,
            reservations: List<Reservation>
        ): CustomerInfoDTO =
            CustomerInfoDTO(
                id = customer.id!!,
                firstName = customer.firstName,
                lastName = customer.lastName,
                phoneNumber = customer.phoneNumber,
                email = customer.email,
                isVip = customer.isVip,
                comments = comments.map { CommentDTO.fromComment(it) }.sortedByDescending { it.dateTime },
                reservations = CustomerReservationsDTO.fromCustomerReservations(reservations.map {
                    CustomerReservationDTO.fromReservation(
                        it
                    )
                }.groupBy { it.state })
            )
    }
}