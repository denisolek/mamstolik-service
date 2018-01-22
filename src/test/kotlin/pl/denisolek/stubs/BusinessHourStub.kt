package pl.denisolek.stubs

import pl.denisolek.core.restaurant.BusinessHour
import java.time.DayOfWeek
import java.time.LocalTime

class BusinessHourStub {
    companion object {
        fun createBusinessHours(
            daysOfWeek: List<DayOfWeek>,
            openTime: LocalTime,
            closeTime: LocalTime
        ): MutableMap<DayOfWeek, BusinessHour> =
            daysOfWeek.map {
                Pair(
                    it, BusinessHour(
                        id = null,
                        openTime = openTime,
                        closeTime = closeTime,
                        isClosed = false
                    )
                )
            }.toMap().toMutableMap()
    }
}