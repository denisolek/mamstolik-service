package pl.denisolek.core.email

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import pl.denisolek.core.customer.Customer
import pl.denisolek.core.user.User

@Component
class EmailService(private val emailSender: EmailSender, private val templateEngine: TemplateEngine) {

    @Value("\${front-web.protocol}://\${front-web.hostname}:\${front-web.port}")
    val WEB: String? = null

//    fun reservationAccepted(reservation: Reservation) {
//        val context = Context()
//        context.setVariable("name", reservation.customer.firstName)
//        context.setVariable("restaurantName", reservation.restaurant.name)
//        context.setVariable("day", reservation.startDateTime.toLocalDate().toString())
//        context.setVariable("hour", reservation.startDateTime.toLocalTime().toString())
//        context.setVariable("peopleCount", reservation.peopleNumber)
//
//        val body = templateEngine.process("reservation-accepted", context)
//        emailSender.sendEmail(reservation.customer.email, "Potwierdzenie rezerwacji.", body)
//    }
//
//    fun reservationCanceled(reservation: Reservation) {
//        val context = Context()
//        context.setVariable("name", reservation.customer.firstName)
//        context.setVariable("restaurantName", reservation.restaurant.name)
//
//        val body = templateEngine.process("reservation-canceled", context)
//        emailSender.sendEmail(reservation.customer.email, "Rezerwacja odwołana.", body)
//    }

    fun registerOwner(user: User) {
        val link = "$WEB/set-password?username=${user.username}&activationKey=${user.activationKey}"
        val context = Context()
        context.setVariable("name", user.firstName)
        context.setVariable("link", link)

        val body = templateEngine.process("register-owner", context)
        emailSender.sendEmail(user.email, "Rejestracja", body, EmailType.REGISTRATION)
    }

    fun lostPassword(user: User, resetKey: String) {
        val link = "$WEB/set-password?username=${user.username}&resetKey=${user.resetPasswordKey}"
        val context = Context()
        context.setVariable("link", link)

        val body = templateEngine.process("lost-password", context)
        emailSender.sendEmail(user.email, "Reset hasła", body, EmailType.LOST_PASSWORD)
    }

    fun smsCode(customer: Customer, code: String) {
        val context = Context()
        context.setVariable("code", code)

        val body = templateEngine.process("sms-code", context)
        emailSender.sendEmail(customer.email, "Kod weryfikacyjny", body, EmailType.SMS_CODE)
    }
}