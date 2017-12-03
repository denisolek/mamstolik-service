package pl.denisolek.panel.image

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import pl.denisolek.Exception.ServiceException
import pl.denisolek.core.image.Image
import pl.denisolek.core.image.ImageService
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.restaurant.RestaurantService
import pl.denisolek.infrastructure.util.isImageType
import pl.denisolek.panel.image.DTO.ImageDTO

@Service
class PanelImageService(private val imageService: ImageService,
                        private val restaurantService: RestaurantService) {

    fun uploadImage(restaurant: Restaurant, imageType: String, image: MultipartFile): ImageDTO {
        validateImage(image)
        val uuidName = imageService.generateUUID()
        imageService.saveFullSize(uuidName, image)
        imageService.saveThumbnail(uuidName, image)
        val newImage = saveImage(image, uuidName, restaurant)
        assignMainImage(imageType, restaurant, newImage)
        return ImageDTO.fromImage(newImage)
    }

    private fun assignMainImage(imageType: String, restaurant: Restaurant, newImage: Image) {
        when (imageType) {
            "main" -> {
                restaurant.mainImage = newImage
                restaurant.images.add(newImage)
                restaurantService.save(restaurant)
            }
        }
    }

    private fun saveImage(image: MultipartFile, uuidName: String, restaurant: Restaurant): Image {
        return imageService.save(Image(
                fileName = image.originalFilename,
                uuid = uuidName,
                restaurant = restaurant
        ))
    }

    private fun validateImage(image: MultipartFile) {
        if (image.isEmpty) throw ServiceException(HttpStatus.BAD_REQUEST, "Image is empty.")
        if (!image.isImageType()) throw ServiceException(HttpStatus.METHOD_NOT_ALLOWED, "Wrong image type.")
    }
}