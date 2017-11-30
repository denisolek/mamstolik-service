package pl.denisolek.core.spot

import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.schema.SchemaItem
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Spot(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null,

        var number: Int,

        var capacity: Int,

        @Column(nullable = false, columnDefinition = "int default 1")
        var minPeopleNumber: Int = 1,

        @ManyToOne
        var restaurant: Restaurant,

        @OneToOne(mappedBy = "spot")
        var schemaItem: SchemaItem? = null
) {
    fun haveReservationsInFuture(): Boolean {
        val reservedSpots = this.restaurant.reservations
                .filter { it.startDateTime.isAfter(LocalDateTime.now()) }
                .flatMap { it.spots }
                .toSet()
        if (!reservedSpots.contains(this)) return false
        return true
    }
}