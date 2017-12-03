package pl.denisolek.panel.image

import org.springframework.web.bind.annotation.RestController

@RestController
class PanelImageController(val panelImageService: PanelImageService): PanelImageApi {
    companion object {
        val API = PanelImageApi.Companion
    }
}