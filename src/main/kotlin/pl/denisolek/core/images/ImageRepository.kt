package pl.denisolek.core.images

import org.springframework.data.jpa.repository.JpaRepository

interface ImageRepository: JpaRepository<Image, Int> {
}