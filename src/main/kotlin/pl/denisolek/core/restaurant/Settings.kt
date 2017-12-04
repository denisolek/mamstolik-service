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
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Settings

        if (id != other.id) return false
        if (localization != other.localization) return false
        if (specialDates != other.specialDates) return false
        if (description != other.description) return false
        if (photos != other.photos) return false
        if (menu != other.menu) return false
        if (schema != other.schema) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + localization.hashCode()
        result = 31 * result + specialDates.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + photos.hashCode()
        result = 31 * result + menu.hashCode()
        result = 31 * result + schema.hashCode()
        return result
    }
}