package pl.denisolek.core.menu.item

import org.springframework.data.jpa.repository.JpaRepository

interface MenuItemRepository: JpaRepository<MenuItem, Int> {
}