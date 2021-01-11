package ru.pricelord.pricelord.input.rest.model.request

data class AddUserItemRequest(
        val userId: String,
        val id: Long? = null,
        val name: String,
        val link: String,
        val isNeedNotification: Boolean
)