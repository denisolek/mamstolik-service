package pl.denisolek.core.schema

import org.springframework.stereotype.Service

@Service
class SchemaService(private val schemaItemRepository: SchemaItemRepository) {
    fun saveSchemaItems(items: List<SchemaItem>) =
        schemaItemRepository.save(items)
}