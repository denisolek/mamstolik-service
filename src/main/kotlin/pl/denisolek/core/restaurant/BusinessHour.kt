package pl.denisolek.core.restaurant

import pl.denisolek.core.BaseEntity
import java.time.LocalTime
import javax.persistence.Entity

@Entity
class BusinessHour(
        var openTime: LocalTime,
        var closeTime: LocalTime
) : BaseEntity()