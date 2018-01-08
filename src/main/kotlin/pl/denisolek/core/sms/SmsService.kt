package pl.denisolek.core.sms

import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service
import pl.denisolek.core.customer.Customer
import pl.denisolek.core.email.EmailService


@Service
class SmsService(val template: RabbitTemplate,
                 val emailService: EmailService,
                 val smsQueue: Queue) {

    private val log = LoggerFactory.getLogger(SmsService::class.java)

    fun sendCode(verificationCode: String, phoneNumber: String, customer: Customer) {
        val message = SmsCodeMessage(verificationCode, phoneNumber)
        this.template.convertAndSend(smsQueue.name, message)
        Thread { emailService.smsCode(customer, verificationCode) }.start()
        log.info(" [RabbitMQ] Added to queue: '$message'")
    }
}