package pl.denisolek.core.menu

import pl.denisolek.core.menu.category.MenuCategory
import javax.persistence.*

@Entity
data class Menu(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null,

        @OneToMany(mappedBy = "menu", cascade = arrayOf(CascadeType.ALL), orphanRemoval = true)
        var categories: List<MenuCategory> = listOf()
)