package garstka.jakub.allegro_repo.controllers

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.lang.RuntimeException

@ControllerAdvice
class RestResponseEntityExceptionHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler(RuntimeException::class)
    fun handleNotFoundException(exception: Exception, request: WebRequest): ResponseEntity<Any> {
        return ResponseEntity("Resource Not Found", HttpHeaders(), HttpStatus.NOT_FOUND)
    }
}