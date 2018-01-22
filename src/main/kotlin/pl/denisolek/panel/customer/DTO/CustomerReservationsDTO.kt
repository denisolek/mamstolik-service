package pl.denisolek.panel.customer.DTO

import pl.denisolek.panel.customer.DTO.ReservationStateDTO.*

data class CustomerReservationsDTO(
    var totalCount: Int,
    var finishedCount: Int,
    var upcomingCount: Int,
    var canceledCount: Int,
    var notAppearedCount: Int,
    var upcoming: List<CustomerReservationDTO>,
    var historical: List<CustomerReservationDTO>
) {
    companion object {
        fun fromCustomerReservations(reservations: Map<ReservationStateDTO, List<CustomerReservationDTO>>): CustomerReservationsDTO =
            CustomerReservationsDTO(
                totalCount = reservations.flatMap { it.value }.groupBy { it }.size,
                finishedCount = reservations[FINISHED]?.size ?: 0,
                upcomingCount = reservations[UPCOMING]?.size ?: 0,
                canceledCount = reservations[CANCELED]?.size ?: 0,
                notAppearedCount = reservations[NOT_APPEARED]?.size ?: 0,
                upcoming = reservations[UPCOMING] ?: listOf(),
                historical = listOf(reservations[FINISHED] ?: listOf(), reservations[CANCELED] ?: listOf()).flatten()
            )
    }
}