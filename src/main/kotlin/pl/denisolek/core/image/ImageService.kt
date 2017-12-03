package pl.denisolek.core.image

import net.coobird.thumbnailator.Thumbnails
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*

@Service
class ImageService(private val imageRepository: ImageRepository) {

    fun save(image: Image) =
            imageRepository.save(image)

    fun generateUUID(): String {
        var exists: Boolean = true
        var uuid = ""

        while (exists) {
            uuid = UUID.randomUUID().toString()
            if (imageRepository.countByUuid(uuid) == 0)
                exists = false
        }
        return uuid
    }

    fun resize(image: MultipartFile, resizedImage: File, width: Int, height: Int) {
        Thumbnails.of(image.inputStream)
                .size(width, height)
                .toFile(resizedImage)
    }

    fun toFile(image: MultipartFile, fullsize: File) {
        Thumbnails.of(image.inputStream)
                .scale(1.0, 1.0)
                .toFile(fullsize)
    }
}