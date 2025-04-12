package com.example.raznarokmobileapp.core.domain.model

import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Serializable
data class Availability(
    val id: Int,
    val location: String,
    val dateFrom: String,
    val dateTo: String,
    val userId: Int,
)

fun Availability.toFormattedString(): String {
    val inputFormatter = DateTimeFormatter.ISO_ZONED_DATE_TIME
    val outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    val from = ZonedDateTime.parse(this.dateFrom, inputFormatter)
    val to = ZonedDateTime.parse(this.dateTo, inputFormatter)

    val formattedFrom = from.format(outputFormatter)
    val formattedTo = to.format(outputFormatter)

    return "$formattedFrom - $formattedTo, $location"
}