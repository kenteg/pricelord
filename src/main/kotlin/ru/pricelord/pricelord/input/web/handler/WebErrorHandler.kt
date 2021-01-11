package ru.pricelord.pricelord.input.web.handler

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.pricelord.pricelord.core.db.errors.UserNotFoundException
import ru.pricelord.pricelord.input.web.handler.ErrorCode.USER_NOT_FOUND

@RestControllerAdvice
class WebErrorHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException::class)
    fun handle(ex: UserNotFoundException): ErrorResponse {
        logger.error(ex.message, ex)
        return getSingleMsgErrorResponse(ex.message, USER_NOT_FOUND)
    }

    fun getSingleMsgErrorResponse(msg: String, code: ErrorCode) =
        ErrorResponse(listOf(ErrorResponseItem(code = code, message = msg)))

    private val logger = LoggerFactory.getLogger(this::class.java)
}