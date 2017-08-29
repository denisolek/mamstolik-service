package pl.denisolek.core.restaurant

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface RestaurantRepository : JpaRepository<Restaurant, Int> {
    @Query("select distinct r from Restaurant as r left join r.address as a left join a.city as c " +
            "where (lower(c.name) = :name)")
    fun findByCityAndAliasesAndIsActive(@Param(value = "name") name: String): List<Restaurant>
}