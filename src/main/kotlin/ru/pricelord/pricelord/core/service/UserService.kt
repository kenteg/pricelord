package ru.pricelord.pricelord.core.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.pricelord.pricelord.core.db.model.User
import ru.pricelord.pricelord.core.db.repository.UserRepository

@Service
class UserService(
        private val userRepository: UserRepository
) {
    fun getUserById(id: String): User? {
        return userRepository.findByIdOrNull(id)
    }

    fun saveUser(id: String, extSystem: String, authToken: String? = null): User {
        return userRepository.save(User(
                id = id,
                authToken = authToken,
                extSystem = extSystem
        ))
    }
}