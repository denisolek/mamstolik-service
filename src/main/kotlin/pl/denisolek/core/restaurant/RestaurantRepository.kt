package pl.denisolek.core.restaurant

import org.springframework.data.jpa.repository.JpaRepository

interface RestaurantRepository : JpaRepository<Restaurant, Int> {
}