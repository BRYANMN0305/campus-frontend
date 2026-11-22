package co.edu.iub.campus_app.data.mapper

import java.time.LocalDateTime

fun String?.toLocalDateTimeOrNow(): LocalDateTime {
    return this
        ?.let { value -> runCatching { LocalDateTime.parse(value) }.getOrNull() }
        ?: LocalDateTime.now()
}
