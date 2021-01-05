package ru.pricelord.pricelord.core.db.service

import org.springframework.stereotype.Service

@Service
class LinkService {
    fun formatLink(link: String): String {
        val afterDoubleSlash = link.substringAfter("//")
        return afterDoubleSlash.replace("www.", "").replace("www", "")
    }
}