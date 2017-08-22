package pl.denisolek.core.infrastructure.exceptions

import javassist.tools.web.BadHttpRequest
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpMediaTypeNotAcceptableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import pl.denisolek.Exception.ServiceException
import javax.persistence.EntityExistsException
import javax.persistence.EntityNotFoundException
import javax.validation.ConstraintViolationException

@ControllerAdvice
class ExceptionHandlerAdvice {
    @ExceptionHandler(ServiceException::class)
    fun handleException(e: ServiceException): ResponseEntity<*> {
        return ResponseEntity.status(e.httpStatus).body(e.body)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun requestHandlerIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<*> {
        if (e.message?.contains("Parameter specified as non-null is null") == true)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("")
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
}