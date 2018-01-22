package pl.denisolek.core.floor

import com.fasterxml.jackson.annotation.JsonIgnore
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.schema.SchemaItem
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Floor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    var name: String,

    @OneToMany(mappedBy = "floor", cascade = arrayOf(CascadeType.ALL), orphanRemoval = true)
    @JsonIgnore
    var schemaItems: MutableList<SchemaItem> = mutableListOf(),

    @ManyToOne
    var restaurant: Restaurant
) {
    fun haveReservationsInFuture(): Boolean {
        val reservedSpots = this.restaurant.reservations
            .filter { it.startDateTime.isAfter(LocalDateTime.now()) }
            .flatMap { it.spots }
            .toSet()
        val conflictItems = this.schemaItems.filter { reservedSpots.contains(it.spot) }
        if (conflictItems.isEmpty()) return false
        return true
    }
}