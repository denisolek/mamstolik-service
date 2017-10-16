package pl.denisolek.core.email

import org.springframework.stereotype.Component
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import pl.denisolek.core.reservation.Reservation
import pl.denisolek.core.user.User

@Component
class EmailService(private val emailSender: EmailSender, private val templateEngine: TemplateEngine) {

    fun reservationAccepted(reservation: Reservation) {
        val context = Context()
        context.setVariable("name", reservation.customer.firstName)
        context.setVariable("restaurantName", reservation.restaurant.name)
        context.setVariable("day", reservation.startDateTime.toLocalDate().toString())
        context.setVariable("hour", reservation.startDateTime.toLocalTime().toString())
        context.setVariable("peopleCount", reservation.peopleNumber)

        val body = templateEngine.process("reservation-accepted", context)
        emailSender.sendEmail(reservation.customer.email, "Potwierdzenie rezerwacji.", body)
    }

    fun reservationCanceled(reservation: Reservation) {
        val context = Context()
        context.setVariable("name", reservation.customer.firstName)
        context.setVariable("restaurantName", reservation.restaurant.name)

        val body = templateEngine.process("reservation-canceled", context)
        emailSender.sendEmail(reservation.customer.email, "Rezerwacja odwołana.", body)
    }

    fun registerOwner(user: User) {
        val context = Context()
        context.setVariable("name", user.firstName)
        context.setVariable("username", user.username)
        context.setVariable("activationKey", user.activationKey)

        val body = templateEngine.process("register-owner", context)
        emailSender.sendEmail(user.email!!, "Rejestracja", body)
    }
}