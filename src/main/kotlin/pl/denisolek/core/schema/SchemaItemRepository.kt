package pl.denisolek.core.schema

import org.springframework.data.jpa.repository.JpaRepository

interface SchemaItemRepository : JpaRepository<SchemaItem, Int> {
}