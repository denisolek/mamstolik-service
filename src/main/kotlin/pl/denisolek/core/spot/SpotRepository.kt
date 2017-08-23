package pl.denisolek.core.spot

import org.springframework.data.jpa.repository.JpaRepository

interface SpotRepository : JpaRepository<Spot, Int> {
}