package pl.denisolek.infrastructure.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.zalando.logbook.BodyFilter
import org.zalando.logbook.BodyFilters
import java.util.*

@Configuration
class LogbookConfig {
    @Bean
    fun bodyFilterSetup(): BodyFilter {
        val properties = HashSet<String>()
        properties.add("access_token")
        properties.add("refresh_token")
        properties.add("firstName")
        properties.add("lastName")
        properties.add("password")
        properties.add("newPassword")
        return BodyFilters.replaceJsonStringProperty(properties, "XXX")
    }
}