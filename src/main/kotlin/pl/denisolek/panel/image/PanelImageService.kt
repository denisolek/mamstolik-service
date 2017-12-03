package pl.denisolek.panel.image

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import pl.denisolek.core.image.Image
import pl.denisolek.core.image.ImageService
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.restaurant.RestaurantService
import pl.denisolek.panel.image.DTO.ImageDTO

@Service
class PanelImageService(private val imageService: ImageService,
                        private val restaurantService: RestaurantService) {

    fun uploadImage(restaurant: Restaurant, imageType: String, image: MultipartFile): ImageDTO {
        imageService.validateImage(image)
        val uuidName = imageService.generateUUID()
        imageService.saveFullSize(uuidName, image)
        imageService.saveThumbnail(uuidName, image)
        val newImage = imageService.saveImage(image, uuidName, restaurant)
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
}