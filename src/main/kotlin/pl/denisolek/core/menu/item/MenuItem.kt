package pl.denisolek.core.menu.item

import pl.denisolek.core.menu.category.MenuCategory
import javax.persistence.*

@Entity
data class MenuItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    var name: String,
    var description: String? = "",
    var price: Float,
    var position: Int,

    @ManyToOne
    var category: MenuCategory
)