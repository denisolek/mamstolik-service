package pl.denisolek.infrastructure

import java.time.LocalDateTime

interface DateTimeInterval {
    var startDateTime: LocalDateTime
    var endDateTime: LocalDateTime

    fun containsInclusiveStartAndEnd(interval: DateTimeInterval)
            = this.startDateTime.isBeforeOrEqual(interval.startDateTime)
            && this.endDateTime.isAfterOrEqual(interval.endDateTime)

    fun isBetweenLocalDateTimes(startDateTime: LocalDateTime, endDateTime: LocalDateTime): Boolean =
            this.startDateTime.isAfterOrEqual(startDateTime) && this.endDateTime.isBeforeOrEqual(endDateTime)

    fun overlapsInclusive(interval: DateTimeInterval)
            = startDateTime <= interval.endDateTime && endDateTime >= interval.startDateTime

    fun overlaps(interval: DateTimeInterval)
            = startDateTime < interval.endDateTime && endDateTime > interval.startDateTime

    fun overlaps(startDateTime: LocalDateTime, endDateTime: LocalDateTime)
            = this.startDateTime <= endDateTime && this.endDateTime <= startDateTime

    fun timeRangeEqual(interval: DateTimeInterval)
            = startDateTime == interval.startDateTime && endDateTime == interval.endDateTime
}