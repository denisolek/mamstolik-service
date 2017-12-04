package pl.denisolek.core.restaurant

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Settings(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null,
        var localization: Boolean = false,
        var specialDates: Boolean = false,
        var description: Boolean = false,
        var photos: Boolean = false,
        var menu: Boolean = false,
        var schema: Boolean = false
)