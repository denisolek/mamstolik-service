package pl.denisolek

import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import pl.denisolek.core.image.StorageProperties
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths

@Component
class StartupListener(properties: StorageProperties) {

    private val baseLocation: Path = Paths.get(properties.BASE_PATH)
    private val tmpLocation: Path = Paths.get(properties.tmpLocation)
    private val fullSizeLocation: Path = Paths.get(properties.fullSizeLocation)
    private val thumbnailLocation: Path = Paths.get(properties.thumbnailLocation)
    private val avatarLocation: Path = Paths.get(properties.avatarLocation)


    @EventListener
    fun handleContextRefresh(event: ContextRefreshedEvent) {
        createDirectory(baseLocation.toString())
        createDirectory(tmpLocation.toString())
        createDirectory(fullSizeLocation.toString())
        createDirectory(thumbnailLocation.toString())
        createDirectory(avatarLocation.toString())
    }

    private fun createDirectory(directory: String) {
        val file = File(directory)
        if (!file.exists()) {
            file.mkdir()
        }
    }
}