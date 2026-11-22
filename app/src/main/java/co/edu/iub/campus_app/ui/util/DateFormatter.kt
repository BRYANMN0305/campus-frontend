package co.edu.iub.campus_app.ui.util

import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

private val spanishLocale = Locale.forLanguageTag("es-CO")
private val dateFormatter = DateTimeFormatter.ofPattern("d MMM yyyy", spanishLocale)
private val dateTimeFormatter = DateTimeFormatter.ofPattern("d MMM yyyy, HH:mm", spanishLocale)

fun LocalDateTime.formatDate(): String = format(dateFormatter)

fun LocalDateTime.formatDateTime(): String = format(dateTimeFormatter)

fun LocalDateTime.relativeLabel(): String {
    val now = LocalDateTime.now()
    if (isAfter(now)) return "ahora"

    val minutes = Duration.between(this, now).toMinutes()
    return when {
        minutes < 1 -> "ahora"
        minutes < 60 -> "hace $minutes min"
        minutes < 24 * 60 -> "hace ${minutes / 60} h"
        minutes < 7 * 24 * 60 -> "hace ${minutes / (24 * 60)} d"
        else -> formatDate()
    }
}
