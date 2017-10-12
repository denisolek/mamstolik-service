package pl.denisolek.stubs

import pl.denisolek.core.customer.Customer
import pl.denisolek.core.reservation.Reservation
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class ReservationStub {
    companion object {
        fun getReservations(): MutableList<Reservation> =
                mutableListOf(
                        createReservation().copy(),
                        createReservation().copy(
                                startDateTime = LocalDateTime.of(LocalDate.of(2017, 11, 1), LocalTime.of(15, 0)),
                                endDateTime = LocalDateTime.of(LocalDate.of(2017, 11, 1), LocalTime.of(15, 30)),
                                verificationCode = 222222),
                        createReservation().copy(
                                startDateTime = LocalDateTime.of(LocalDate.of(2017, 11, 1), LocalTime.of(16, 0)),
                                endDateTime = LocalDateTime.of(LocalDate.of(2017, 11, 1), LocalTime.of(16, 30)),
                                verificationCode = 333333)
                )

        fun createReservation(): Reservation =
                Reservation(
                        restaurant = RestaurantStub.getRestaurantForStubs(),
                        customer = Customer(email = "reservation@test.pl", firstName = "Rezerwator", lastName = "Rezerwujący", phoneNumber = "123123123"),
                        startDateTime = LocalDateTime.of(LocalDate.of(2017, 11, 1), LocalTime.of(14, 0)),
                        duration = Duration.ofHours(1),
                        endDateTime = LocalDateTime.of(LocalDate.of(2017, 11, 1), LocalTime.of(14, 30)),
                        peopleNumber = 5,
                        state = Reservation.ReservationState.ACCEPTED,
                        isVerified = true,
                        verificationCode = 111111,
                        spots = mutableListOf()
                )
    }
}