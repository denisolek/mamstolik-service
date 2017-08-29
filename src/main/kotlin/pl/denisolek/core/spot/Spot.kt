package pl.denisolek.core.spot

import pl.denisolek.core.BaseEntity
import pl.denisolek.core.restaurant.Restaurant
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
class Spot(
        var capacity: Int,

        @ManyToOne
        var restaurant: Restaurant) : BaseEntity()