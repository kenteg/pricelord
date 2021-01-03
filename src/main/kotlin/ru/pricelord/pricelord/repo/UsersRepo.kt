package ru.pricelord.pricelord.repo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.pricelord.pricelord.model.User

@Repository
interface UsersRepo : JpaRepository<User, Long>