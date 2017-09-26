package pl.denisolek.core.menu

import org.springframework.data.jpa.repository.JpaRepository

interface MenuCategoryRepository: JpaRepository<MenuCategory, Int> {
}