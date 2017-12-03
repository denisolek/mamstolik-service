package pl.denisolek.core.image

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import pl.denisolek.infrastructure.*

@ConfigurationProperties("storage")
@Component
class StorageProperties {
    private val BASE_PATH = "$FILES"
    private val tmpLocation = "$BASE_PATH/$TMP"
    internal val fullSizeLocation = "$BASE_PATH/$FULL_SIZE"
    internal val thumbnailLocation = "$BASE_PATH/$THUMBNAIL"
    private val avatarLocation = "$BASE_PATH/$AVATAR"
}