package pl.denisolek.core

data class SmsCodeMessage(
        var code: String,
        var phoneNumber: String
)