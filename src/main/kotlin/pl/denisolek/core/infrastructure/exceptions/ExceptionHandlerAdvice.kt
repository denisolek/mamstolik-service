package pl.denisolek.core.infrastructure.exceptions

import javassist.tools.web.BadHttpRequest
import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.expression.AccessException
import org.springframework.expression.spel.SpelEvaluationException
import org.springframework.expression.spel.SpelMessage
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpMediaTypeNotAcceptableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.multipart.MultipartException
import pl.denisolek.Exception.ServiceException
import javax.persistence.EntityExistsException
import javax.persistence.EntityNotFoundException
import javax.validation.ConstraintViolationException

@ControllerAdvice
class ExceptionHandlerAdvice {

    var ex: Exception? = null
    private val log = LoggerFactory.getLogger(ExceptionHandlerAdvice::class.java)

    @ExceptionHandler(ServiceException::class)
    fun handleException(e: ServiceException): ResponseEntity<*> {
        ex = e
        log.error("$e.stackTrace $e.cause?.message")
        return ResponseEntity.status(e.httpStatus).body(e.body)
    }

    @ExceptionHandler(MultipartException::class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun requestHandlerMultipartException(): ResponseEntity<*> {
        val e = ServiceException(HttpStatus.BAD_REQUEST, "Invalid file size.")
        return ResponseEntity.status(e.httpStatus).body(e.body)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun requestHandlerIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<*> {
        ex = e
        if (e.message?.contains("Parameter specified as non-null is null") == true)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("")

        var ex: Exception = e
        loop@ while (ex.cause != ex.cause?.cause) {
            return when (ex.cause) {
                is AccessException -> ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied")
                is SpelEvaluationException -> {
                    when ((ex.cause as SpelEvaluationException).messageCode) {
                        SpelMessage.PROPERTY_OR_FIELD_NOT_READABLE_ON_NULL -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                            ""
                        )
                        else -> {
                            log.info((ex.cause as SpelEvaluationException).messageCode.name)
                            log.info((ex.cause as SpelEvaluationException).message)
                            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("")
                        }
                    }
                }
                else -> {
                    ex = ex.cause as Exception
                    continue@loop
                }
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("")
    }

    @ExceptionHandler(EntityNotFoundException::class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    fun requestHandlerNotFound() {
    }

    @ExceptionHandler(EntityExistsException::class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ResponseBody
    fun requestHandlerConflict() {
    }

    @ExceptionHandler(BadHttpRequest::class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun requestHandlerBadRequest() {
    }

    @ExceptionHandler(ConstraintViolationException::class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun requestHandlerConstraintViolationException() {
    }

    @ExceptionHandler(InternalError::class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    fun requestHandlerInternalServerError() {
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException::class)
    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    fun requestHandlerMediaTypeNotAcceptable() {
    }

    @ExceptionHandler(DataIntegrityViolationException::class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ResponseBody
    fun requestHandlerDataIntegrityViolationException() {
    }

    fun clearException() {
        this.ex = null
    }
}