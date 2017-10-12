package pl.denisolek.Email;

import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.denisolek.core.reservation.Reservation;

@Component
public class EmailService {
    private final EmailSender emailSender;
    private final TemplateEngine templateEngine;

    public EmailService(EmailSender emailSender, TemplateEngine templateEngine) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
    }

    public void reservationAccepted(Reservation reservation) {
        Context context = new Context();
        context.setVariable("name", reservation.getCustomer().getFirstName());
        context.setVariable("restaurantName", reservation.getRestaurant().getName());
        context.setVariable("day", reservation.getStartDateTime().toLocalDate().toString());
        context.setVariable("hour", reservation.getStartDateTime().toLocalTime().toString());
        context.setVariable("peopleCount", reservation.getPeopleNumber());

        String body = templateEngine.process("reservation-accepted", context);
        emailSender.sendEmail(reservation.getCustomer().getEmail(), "Potwierdzenie rezerwacji.", body);
    }

    public void reservationCanceled(Reservation reservation) {
        Context context = new Context();
        context.setVariable("name", reservation.getCustomer().getFirstName());
        context.setVariable("restaurantName", reservation.getRestaurant().getName());

        String body = templateEngine.process("reservation-canceled", context);
        emailSender.sendEmail(reservation.getCustomer().getEmail(), "Rezerwacja odwołana.", body);
    }
}
