package pl.denisolek.stubs

import pl.denisolek.core.customer.Customer
import pl.denisolek.core.reservation.Reservation
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime

class ReservationStub {
    companion object {
        fun getReservations(): MutableList<Reservation> =
                mutableListOf(
                        createReservation().copy(),
                        createReservation().copy(startTime = LocalTime.of(15, 0), endTime = LocalTime.of(15, 30), verificationCode = 222222),
                        createReservation().copy(startTime = LocalTime.of(16, 0), endTime = LocalTime.of(16, 30), verificationCode = 333333)
                )

        fun createReservation(): Reservation =
                Reservation(
                        date = LocalDate.of(2017, 11, 1),
                        restaurant = RestaurantStub.getRestaurantForStubs(),
                        customer = Customer(email = "reservation@test.pl", name = "Rezerwator", surname = "Rezerwujący", phoneNumber = "123123123"),
                        startTime = LocalTime.of(14, 0),
                        duration = Duration.ofHours(1),
                        endTime = LocalTime.of(14, 30),
                        peopleNumber = 5,
                        state = Reservation.ReservationState.ACCEPTED,
                        isVerified = true,
                        verificationCode = 111111
                )
    }
}