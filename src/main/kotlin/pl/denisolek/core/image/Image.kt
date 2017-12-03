package pl.denisolek.core.image

import pl.denisolek.core.restaurant.Restaurant
import javax.persistence.*

@Entity
data class Image(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null,
        var fileName: String,
        var uuid: String,
        @ManyToOne
        var restaurant: Restaurant? = null
)