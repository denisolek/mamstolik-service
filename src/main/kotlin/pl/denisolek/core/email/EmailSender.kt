package pl.denisolek.core.email

interface EmailSender {
    fun sendEmail(target: String, subject: String, content: String, emailType: EmailType)
}

enum class EmailType {
    REGISTRATION,
    LOST_PASSWORD,
    SMS_CODE
}