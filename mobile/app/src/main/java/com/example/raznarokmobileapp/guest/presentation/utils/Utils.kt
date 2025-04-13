package com.example.raznarokmobileapp.guest.presentation.utils

import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

//const val API_BASE_URL = "http://172.98.1.172:3000"
const val API_BASE_URL = "http://172.98.1.168:3000"

fun timeAgoFromIso(isoString: String): String {
    val timestamp = Instant.parse(isoString)
    val now = Instant.now()
    val duration = Duration.between(timestamp, now)

    return if (duration.toMinutes() < 1) {
        "Now"
    } else {
        "${duration.toMinutes()} minutes ago"
    }
}

fun formatTimestampToTimeOrNow(isoString: String): String {
    val timestamp = Instant.parse(isoString)
    val now = Instant.now()

    val duration = Duration.between(timestamp, now)
    return if (duration.toMinutes() < 1) {
        "Just now"
    } else {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
            .withZone(ZoneId.systemDefault())

        formatter.format(timestamp)
    }
}

fun convertDatesToString(start: Long?, end: Long?): String {
    if (start == null || end == null) {
        return "Select Dates"
    }
    val startDate = start.toLocalDate()
    val startDateText = startDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
    val endDate = end.toLocalDate()
    val endDateText = endDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
    return "$startDateText - $endDateText"
}

fun Long.toLocalDate(): LocalDate {
    return Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}
