package ru.pricelord.pricelord.core.db.model

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "active_operation")
data class ActiveOperation(
    @field:Id
    val id: String? = null,
    @field:Indexed(unique = true)
    val userId: String,
    @field:LastModifiedDate
    val updateDate: LocalDateTime? = null,
    val operation: OperationType
)

enum class OperationType {
    ADD_ITEM,
    ADD_ITEM_LINK,
    ADD_ITEM_NAME,
    EDIT_ITEM,
    EDIT_ITEM_LINK,
    EDIT_ITEM_NAME,
    DELETE_ITEM
}
