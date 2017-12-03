package pl.denisolek.core.images

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@ConfigurationProperties("storage")
@Component
class StorageProperties {
    private val BASE_PATH = "files"
    private val tmpLocation =  "$BASE_PATH/tmp"
    private val fullSizeLocation = "$BASE_PATH/fullsize"
    private val thumbnailLocation = "$BASE_PATH/thumbnail"
    private val avatarLocation = "$BASE_PATH/avatar"
}