package pl.denisolek.core.spot

import pl.denisolek.core.BaseEntity
import pl.denisolek.core.restaurant.Restaurant
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
data class Spot(
        var capacity: Int,

        @Column(nullable = false, columnDefinition = "int default 1")
        var minPeopleNumber: Int = 1,

        @ManyToOne
        var restaurant: Restaurant) : BaseEntity()