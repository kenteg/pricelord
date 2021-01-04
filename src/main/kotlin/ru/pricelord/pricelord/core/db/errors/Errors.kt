package ru.pricelord.pricelord.core.db.errors

import java.lang.RuntimeException

class UserNotFoundException(
        override val message: String
): RuntimeException()