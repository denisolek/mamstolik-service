package pl.denisolek.guest.restaurant.DTO

import pl.denisolek.core.menu.category.MenuCategory
import pl.denisolek.core.menu.item.MenuItem

data class MenuCategoryDTO(
        var id: Int? = null,
        var name: String,
        var description: String? = "",
        var position: Int,
        var items: List<MenuItemDTO>
) {
    companion object {
        fun fromMenuCategory(menuCategory: MenuCategory): MenuCategoryDTO =
                MenuCategoryDTO(
                        id = menuCategory.id,
                        description = menuCategory.description,
                        name = menuCategory.name,
                        position = menuCategory.position,
                        items = menuCategory.items.map { MenuItemDTO.fromMenuItem(it) }.sortedBy { it.position }
                )
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
    }
}