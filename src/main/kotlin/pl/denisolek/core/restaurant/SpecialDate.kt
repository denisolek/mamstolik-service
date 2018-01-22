package pl.denisolek.core.restaurant

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
)