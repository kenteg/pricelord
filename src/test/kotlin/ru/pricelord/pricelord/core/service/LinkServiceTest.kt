package ru.pricelord.pricelord.core.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream

internal class LinkServiceTest {

    private val linkService = LinkService()

    @ParameterizedTest(name = "format link {0}")
    @ArgumentsSource(LinkProvider::class)
    fun formatLink(link: String, expectedLink: String) {
        val formattedLink = linkService.formatLink(link)

        assertEquals(expectedLink, formattedLink)
    }

    class LinkProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext?): Stream<Arguments> = Stream.of(
            arguments(
                "https://mvideo.ru/1111",
                "mvideo.ru/1111"
            ),
            arguments(
                "http://www.mvideo.ru/2222",
                "mvideo.ru/2222"
            ),
            arguments(
                "www.mvideo.ru/3333",
                "mvideo.ru/3333"
            )
        )
    }
}