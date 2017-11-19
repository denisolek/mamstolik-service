package pl.denisolek.core.schema

import net.minidev.json.annotate.JsonIgnore
import pl.denisolek.core.spot.Spot
import javax.persistence.*

@Entity
data class SchemaItem(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null,
        var x: Float = 0.0f,
        var y: Float = 0.0f,
        var width: Int = 0,
        var height: Int = 0,
        var rotation: Float = 0.0f,

        @Enumerated(EnumType.STRING)
        var type: Type,

        @Enumerated(EnumType.STRING)
        var tableType: TableType? = null,

        @Enumerated(EnumType.STRING)
        var itemType: ItemType? = null,

        @Enumerated(EnumType.STRING)
        var wallItemType: WallItemType? = null,

        @OneToOne(cascade = arrayOf(CascadeType.PERSIST))
        var spot: Spot? = null,

        @ManyToOne
        @JsonIgnore
        var floor: Floor
) {
    enum class Type {
        WALL, // 1
        TABLE, // 2
        ITEM, // 3
        WALL_ITEM // 4
    }

    enum class TableType {
        TWO, // 1
        TWO_ROUND, // 2
        THREE, // 3
        THREE_ROUND, // 4
        FOUR, // 5
        FOUR_RECT, // 6
        FOUR_ROUND, // 7
        FIVE_ROUND, // 8
        FIVE_RECT_1, // 9
        FIVE_RECT_2, // 10
        FIVE_RECT_3, // 11
        SIX_RECT_1, // 12
        SIX_RECT_2, // 13
        SIX_ROUND, // 14
        EIGHT_RECT, // 15
        EIGHT_ROUND // 16
    }

    enum class ItemType {
        TOILET
    }

    enum class WallItemType {
        WINDOW,
        DOOR
    }
}