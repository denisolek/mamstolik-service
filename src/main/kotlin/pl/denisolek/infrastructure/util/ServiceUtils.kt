package pl.denisolek.infrastructure.util

import org.apache.commons.lang3.RandomStringUtils

fun generateUsernameString(): String {
    return "ms" + RandomStringUtils.randomNumeric(6)
}