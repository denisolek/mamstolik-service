package pl.denisolek.guest.restaurant.DTO

import pl.denisolek.core.menu.Menu
import pl.denisolek.core.menu.category.MenuCategory
import pl.denisolek.core.menu.item.MenuItem

data class MenuCategoryDTO(
    var id: Int? = null,
    var name: String,
    var description: String? = "",
    var position: Int,
    var items: MutableList<MenuItemDTO>
) {
    companion object {
        fun fromMenuCategory(menuCategory: MenuCategory): MenuCategoryDTO =
            MenuCategoryDTO(
                id = menuCategory.id,
                description = menuCategory.description,
                name = menuCategory.name,
                position = menuCategory.position,
                items = menuCategory.items.map { MenuItemDTO.fromMenuItem(it) }.sortedBy { it.position }.toMutableList()
            )

        fun toNewCategory(dtoCategory: MenuCategoryDTO, menu: Menu): MenuCategory {
            val menuCategory = MenuCategory(
                name = dtoCategory.name,
                description = dtoCategory.description,
                position = dtoCategory.position,
                menu = menu
            )
            menuCategory.items.addAll(
                dtoCategory.items.map {
                    MenuItemDTO.toNewItem(it, menuCategory)
                }.toMutableList()
            )
            return menuCategory
        }
    }
}

data class MenuItemDTO(
    var id: Int? = null,
    var name: String,
    var description: String? = "",
    var price: Float,
    var position: Int
) {
    companion object {
        fun fromMenuItem(menuItem: MenuItem): MenuItemDTO =
            MenuItemDTO(
                id = menuItem.id,
                name = menuItem.name,
                description = menuItem.description,
                price = menuItem.price,
                position = menuItem.position
            )

        fun toNewItem(dtoItem: MenuItemDTO, menuCategory: MenuCategory): MenuItem =
            MenuItem(
                name = dtoItem.name,
                description = dtoItem.description,
                price = dtoItem.price,
                position = dtoItem.position,
                category = menuCategory
            )
    }
}