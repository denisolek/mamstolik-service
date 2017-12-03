package pl.denisolek

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import pl.denisolek.core.image.StorageProperties
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import javax.annotation.PreDestroy

@Profile("dev")
@Component
class DestroyCleanup(properties: StorageProperties) {

    private val baseLocation: Path = Paths.get(properties.BASE_PATH)

    @PreDestroy
    fun filesCleanup() {
        removeDirectory(baseLocation.toString())
    }

    private fun removeDirectory(directory: String) {
        val file = File(directory)
        if (file.exists())
            file.deleteRecursively()
    }
}