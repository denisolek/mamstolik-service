package pl.denisolek.core.restaurant

import java.time.LocalTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class BusinessHour(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    var openTime: LocalTime = LocalTime.of(8, 0),
    var closeTime: LocalTime = LocalTime.of(20, 0),
    var isClosed: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BusinessHour

        if (id != other.id) return false
        if (openTime != other.openTime) return false
        if (closeTime != other.closeTime) return false
        if (isClosed != other.isClosed) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + openTime.hashCode()
        result = 31 * result + closeTime.hashCode()
        result = 31 * result + isClosed.hashCode()
        return result
    }
}