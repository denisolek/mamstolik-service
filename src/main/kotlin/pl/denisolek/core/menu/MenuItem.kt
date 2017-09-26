package pl.denisolek.core.menu

import javax.persistence.*

@Entity
data class MenuItem(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null,
        var name: String,
        var price: Float,
        var position: Int,

        @ManyToOne
        var category: MenuCategory
)