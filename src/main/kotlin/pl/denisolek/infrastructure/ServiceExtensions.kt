package pl.denisolek.infrastructure

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


fun LocalDate.isBeforeOrEqual(localDate: LocalDate)
        = !this.isAfter(localDate)

fun LocalDate.isAfterOrEqual(localDate: LocalDate)
        = !this.isBefore(localDate)

fun LocalTime.isBeforeOrEqual(localTime: LocalTime)
        = !this.isAfter(localTime)

fun LocalTime.isAfterOrEqual(localTime: LocalTime)
        = !this.isBefore(localTime)

fun LocalTime.isInsideInclusive(startTime: LocalTime, endTime: LocalTime)
        = this.isAfterOrEqual(startTime) && this.isBeforeOrEqual(endTime)

fun LocalTime.isInsideInclusiveStart(startTime: LocalTime, endTime: LocalTime)
        = this.isAfterOrEqual(startTime) && this.isBefore(endTime)

fun LocalTime.isInsideInclusiveEnd(startTime: LocalTime, endTime: LocalTime)
        = this.isAfter(startTime) && this.isBeforeOrEqual(endTime)

fun LocalDateTime.isBeforeOrEqual(localDateTime: LocalDateTime)
        = !this.isAfter(localDateTime)

fun LocalDateTime.isAfterOrEqual(localDateTime: LocalDateTime)
        = !this.isBefore(localDateTime)

fun LocalDateTime.isInsideInclusive(startTime: LocalDateTime, endTime: LocalDateTime)
        = this.isAfterOrEqual(startTime) && this.isBeforeOrEqual(endTime)

fun LocalDateTime.isInsideInclusiveStart(startTime: LocalDateTime, endTime: LocalDateTime)
        = this.isAfterOrEqual(startTime) && this.isBefore(endTime)

fun LocalDateTime.isInsideInclusiveEnd(startTime: LocalDateTime, endTime: LocalDateTime)
        = this.isAfter(startTime) && this.isBeforeOrEqual(endTime)
