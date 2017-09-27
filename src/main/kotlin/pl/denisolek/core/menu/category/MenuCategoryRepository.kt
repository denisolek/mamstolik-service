package pl.denisolek.core.menu.category

import org.springframework.data.jpa.repository.JpaRepository
import pl.denisolek.core.menu.category.MenuCategory

interface MenuCategoryRepository: JpaRepository<MenuCategory, Int> {
}