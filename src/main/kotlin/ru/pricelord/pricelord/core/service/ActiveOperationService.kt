package ru.pricelord.pricelord.core.service

import org.springframework.stereotype.Component
import ru.pricelord.pricelord.core.db.model.ActiveOperation
import ru.pricelord.pricelord.core.db.model.OperationType
import ru.pricelord.pricelord.core.db.repository.ActiveOperationRepository

@Component
class ActiveOperationService(
    private val activeOperationRepository: ActiveOperationRepository
) {
    fun getActiveOperation(userId: String) = activeOperationRepository.findByUserId(userId)

    fun saveActiveOperation(operation: OperationType, userId: String) {
        val activeOperation = getActiveOperation(userId) ?: activeOperationRepository.save(
            ActiveOperation(
                userId = userId,
                operation = operation
            )
        )

        activeOperation.apply {
            operation
        }
    }
}