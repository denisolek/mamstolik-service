package pl.denisolek.core.scheme

import net.minidev.json.annotate.JsonIgnore
import pl.denisolek.core.spot.Spot
import javax.persistence.*

@Entity
data class SchemeItem(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null,
        var type: ItemType,
        var x: Float = 0.0f,
        var y: Float = 0.0f,
        var width: Int = 0,
        var height: Int = 0,
        var rotation: Float = 0.0f,

        @OneToOne(cascade = arrayOf(CascadeType.ALL))
        var spot: Spot? = null,

        @ManyToOne
        @JsonIgnore
        var floor: Floor
) {
    enum class ItemType {
        WALL,
        TABLE,
        ITEM,
        WALL_ITEM
    }
}