package pl.denisolek.core.restaurant

import org.springframework.http.HttpStatus
import pl.denisolek.Exception.ServiceException
import java.time.LocalDate
import javax.persistence.*

@Entity
class SpecialDate(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null,
        var date: LocalDate,
        var description: String? = null,
        @OneToOne(cascade = arrayOf(CascadeType.ALL))
        var businessHour: BusinessHour,
        @ManyToOne
        var restaurant: Restaurant
) {
    fun canBeRemoved(): Boolean {
        this.restaurant.reservations.filter {
            it.startDateTime.toLocalDate() == date
        }.forEach {
            if (!it.isInsideBusinessHours(restaurant.getBusinessHoursForDate(date)!!))
                throw ServiceException(HttpStatus.BAD_REQUEST, "Reservations from removed special date doesn't fit in regular businessHours for $date (${date.dayOfWeek})")
        }
        return true
    }
}