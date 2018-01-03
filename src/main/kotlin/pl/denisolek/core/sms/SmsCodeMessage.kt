package pl.denisolek.core.sms

data class SmsCodeMessage(
        var code: String,
        var phoneNumber: String
)