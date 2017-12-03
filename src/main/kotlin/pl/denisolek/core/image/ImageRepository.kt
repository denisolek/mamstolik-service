package pl.denisolek.core.image

import org.springframework.data.jpa.repository.JpaRepository

interface ImageRepository : JpaRepository<Image, Int> {
    fun countByUuid(uuid: String): Int
    fun findByUuid(uuid: String): Image?
}