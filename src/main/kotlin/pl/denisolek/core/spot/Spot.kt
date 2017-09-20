package pl.denisolek.core.spot

import pl.denisolek.core.restaurant.Restaurant
import javax.persistence.*

@Entity
data class Spot(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null,

        var capacity: Int,

        @Column(nullable = false, columnDefinition = "int default 1")
        var minPeopleNumber: Int = 1,

        @ManyToOne
        var restaurant: Restaurant)