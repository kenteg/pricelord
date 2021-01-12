package ru.pricelord.pricelord.core.errors

class UserNotFoundException(
        override val message: String
) : RuntimeException()

class StoreNotFoundException(
        override val message: String
) : RuntimeException()