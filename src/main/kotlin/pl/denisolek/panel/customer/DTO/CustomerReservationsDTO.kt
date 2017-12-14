package pl.denisolek.panel.customer.DTO

data class CustomerReservationsDTO(
        var totalCount: Int,
        var finishedCount: Int,
        var upcomingCount: Int,
        var canceledCount: Int,
        var notAppearedCount: Int,
        var upcoming: List<CustomerReservationDTO>,
        var historical: List<CustomerReservationDTO>
)