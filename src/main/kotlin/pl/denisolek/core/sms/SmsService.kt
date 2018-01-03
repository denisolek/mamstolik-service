package pl.denisolek.core.sms

import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service


@Service
class SmsService(val template: RabbitTemplate,
                 val smsQueue: Queue) {

    private val log = LoggerFactory.getLogger(SmsService::class.java)

    fun sendCode(verificationCode: String, phoneNumber: String) {
        val message = SmsCodeMessage(verificationCode, phoneNumber)
        this.template.convertAndSend(smsQueue.name, message)
        log.info(" [RabbitMQ] Added to queue: '$message'")
    }
}