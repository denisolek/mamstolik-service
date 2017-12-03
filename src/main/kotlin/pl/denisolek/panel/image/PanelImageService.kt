package pl.denisolek.panel.image

import org.springframework.stereotype.Service
import pl.denisolek.core.image.ImageService

@Service
class PanelImageService(private val imageService: ImageService) {
}