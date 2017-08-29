package pl.denisolek.infrastructure

import java.time.LocalDate
import java.time.LocalTime


fun LocalDate.isBeforeOrEqual(localDate: LocalDate)
        = !this.isAfter(localDate)

fun LocalDate.isAfterOrEqual(localDate: LocalDate)
        = !this.isBefore(localDate)

fun LocalTime.isBeforeOrEqual(localTime: LocalTime)
        = !this.isAfter(localTime)

fun LocalTime.isAfterOrEqual(localTime: LocalTime)
        = !this.isBefore(localTime)
