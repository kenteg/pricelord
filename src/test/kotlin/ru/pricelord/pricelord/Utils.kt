package ru.pricelord.pricelord

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.io.File

val objectMapper = ObjectMapper().apply {
    registerModule(KotlinModule())
    registerModule(JavaTimeModule())
    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
    disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
}

fun getFileAsString(path: String) =
    File(PriceLordApplication::class.java.classLoader.getResource(path).toURI()).readText()

inline fun <reified T> getObject(path: String): T = objectMapper.readValue(getFileAsString(path), T::class.java)