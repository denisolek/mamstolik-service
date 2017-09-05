package pl.denisolek.core.address

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface CityRepository : JpaRepository<City, Int> {

    @Query("select distinct c from City as c left join c.aliases as a " +
            "where (lower(c.name) like lower(CONCAT(:name, '%'))) " +
            "or (lower(a.name) like lower(CONCAT(:name, '%'))) " +
            "order by c.name asc")
    fun findPartlyByNameOrAlias(@Param(value = "name") name: String): List<City>
}