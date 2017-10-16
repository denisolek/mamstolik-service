package pl.denisolek.core.email

interface EmailSender {
    fun sendEmail(target: String, subject: String, content: String)
}