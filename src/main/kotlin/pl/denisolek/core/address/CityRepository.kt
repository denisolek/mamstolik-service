package pl.denisolek.core.address

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import pl.denisolek.core.restaurant.Restaurant

interface CityRepository: JpaRepository<City, Int> {

    @Query("select distinct r from Restaurant as r left join r.address as a left join a.city as c left join c.aliases as al " +
            //            "where (lower(c.name) = :name)")
            "where (lower(al.name) = :name)")
    fun findByCityAndAliasesAndIsActive(@Param(value = "name") name: String): List<Restaurant>
}