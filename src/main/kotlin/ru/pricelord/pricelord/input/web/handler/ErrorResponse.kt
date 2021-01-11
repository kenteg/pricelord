package ru.pricelord.pricelord.input.web.handler

import com.fasterxml.jackson.annotation.JsonInclude

data class ErrorResponse(
    val errors: List<ErrorResponseItem> = emptyList()
)

data class ErrorResponseItem(
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val field: String? = null,
    val code: ErrorCode,
    val message: String
)

enum class ErrorCode {
    USER_NOT_FOUND
}

