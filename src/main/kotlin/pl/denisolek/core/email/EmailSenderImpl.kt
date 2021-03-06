package pl.denisolek.core.email

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import java.io.UnsupportedEncodingException
import javax.mail.MessagingException

@Service
class EmailSenderImpl(private val javaMailSender: JavaMailSender) : EmailSender {

    @Value("\${spring.mail.username}")
    internal lateinit var MAIL_USERNAME: String

    private val log = LoggerFactory.getLogger(EmailSenderImpl::class.java)

    override fun sendEmail(target: String, subject: String, content: String, emailType: EmailType) {
        val mail = javaMailSender.createMimeMessage()
        try {
            val helper = MimeMessageHelper(mail, true)
            helper.setTo(target)
            helper.setReplyTo(MAIL_USERNAME)
            helper.setFrom(MAIL_USERNAME, "MamStolik.pl")
            helper.setSubject(subject)
            helper.setText(content, true)
        } catch (e: MessagingException) {
            e.printStackTrace()
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        javaMailSender.send(mail)
        log.info(" [EmailService] Send type '$emailType' to '$target'")
    }
}