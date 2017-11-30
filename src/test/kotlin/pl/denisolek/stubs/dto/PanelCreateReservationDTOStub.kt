package pl.denisolek.stubs.dto

import pl.denisolek.panel.reservation.DTO.PanelCreateReservationDTO
import pl.denisolek.panel.reservation.DTO.ReservationCustomerDTO
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class PanelCreateReservationDTOStub {
    companion object {
        fun getPanelCreateReservationDTOStub() = PanelCreateReservationDTO(
                customer = getReservationCustomer(),
                peopleNumber = 3,
                dateTime = getDateTime(),
                spots = listOf(1),
                note = "NoteStub"
        )

        private fun getDateTime() = LocalDateTime.of(LocalDate.of(2018, 3, 30), LocalTime.of(14, 0))

        private fun getReservationCustomer(): ReservationCustomerDTO {
            return ReservationCustomerDTO(
                    firstName = "NameStub",
                    lastName = "SurnameStub",
                    email = "stub@test.pl",
                    phoneNumber = "123123123"
            )
        }
    }
}