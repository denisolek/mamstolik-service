package pl.denisolek.core.spot

import com.fasterxml.jackson.annotation.JsonIgnore
import pl.denisolek.core.BaseEntity
import pl.denisolek.core.restaurant.Restaurant
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class Spot(
        var capacity: Int,

        @ManyToOne
        @JoinColumn
        @JsonIgnore
        var restaurant: Restaurant) : BaseEntity()