package pl.denisolek.core.image

import net.coobird.thumbnailator.Thumbnails
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import pl.denisolek.Exception.ServiceException
import pl.denisolek.infrastructure.AVATAR
import pl.denisolek.infrastructure.FULL_SIZE
import pl.denisolek.infrastructure.THUMBNAIL
import pl.denisolek.infrastructure.TMP
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

@Service
class ImageService(private val imageRepository: ImageRepository,
                   storageProperties: StorageProperties) {


    private val fullSizeLocation: Path = Paths.get(storageProperties.fullSizeLocation)
    private val thumbnailLocation: Path = Paths.get(storageProperties.thumbnailLocation)
    private val avatarLocation: Path = Paths.get(storageProperties.avatarLocation)

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

    fun toFile(image: MultipartFile, fullSize: File) {
        Thumbnails.of(image.inputStream)
                .scale(1.0, 1.0)
                .toFile(fullSize)
    }

    fun saveFullSize(uuidName: String, image: MultipartFile) {
        val fullSize = File("/$TMP/${FULL_SIZE}_$uuidName.png")
        toFile(image, fullSize)
        Files.copy(fullSize.inputStream(), fullSizeLocation.resolve("$uuidName.png"))
    }

    fun saveThumbnail(uuidName: String, image: MultipartFile) {
        val thumbnail = File("/$TMP/${THUMBNAIL}_$uuidName.png")
        resize(image, thumbnail, 300, 300)
        Files.copy(thumbnail.inputStream(), thumbnailLocation.resolve("$uuidName.png"))
    }

    fun loadAsResource(imageType: String, uuid: String): Resource {
        val image = imageRepository.findByUuid(uuid) ?: throw ServiceException(HttpStatus.NOT_FOUND, "Image not found")
        val file = when (imageType) {
            FULL_SIZE -> fullSizeLocation.resolve("${image.uuid}.png")
            THUMBNAIL -> thumbnailLocation.resolve("${image.uuid}.png")
            AVATAR -> avatarLocation.resolve("${image.uuid}.png")
            else -> throw ServiceException(HttpStatus.NOT_FOUND, "Image not found")
        }

        val resource = UrlResource(file.toUri())
        return when {
            (resource.exists() || resource.isReadable) -> resource
            else -> throw ServiceException(HttpStatus.NOT_FOUND, "Image not found")
        }
    }
}