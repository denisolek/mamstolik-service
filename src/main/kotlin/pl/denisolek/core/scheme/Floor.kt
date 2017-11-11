package pl.denisolek.core.scheme

import com.fasterxml.jackson.annotation.JsonIgnore
import pl.denisolek.core.restaurant.Restaurant
import javax.persistence.*

@Entity
data class Floor(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null,

        var name: String,

        @OneToMany(mappedBy = "floor", cascade = arrayOf(CascadeType.ALL), orphanRemoval = true)
        @JsonIgnore
        var tables: MutableList<SchemeItem> = mutableListOf(),

        @ManyToOne
        var restaurant: Restaurant
)