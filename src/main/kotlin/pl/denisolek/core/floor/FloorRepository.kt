package pl.denisolek.core.floor

import org.springframework.data.jpa.repository.JpaRepository

interface FloorRepository : JpaRepository<Floor, Int> {
}