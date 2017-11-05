package pl.denisolek.core.menu.category

import org.springframework.data.jpa.repository.JpaRepository

interface MenuCategoryRepository : JpaRepository<MenuCategory, Int> {
}