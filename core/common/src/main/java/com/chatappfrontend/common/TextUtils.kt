package com.chatappfrontend.common

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import java.time.ZoneId
import java.time.ZonedDateTime

fun formatMessageTime(timestamp: String): String {
    val localTime = getLocalTime(timestamp)
    val outputFormatter = DateTimeFormatter.ofPattern("h:mm a")
    return localTime.format(outputFormatter)
}

fun formatChatDate(timestamp: String): String {
    val localTime = getLocalTime(timestamp)
    val date = localTime.toLocalDate()
    val today = LocalDate.now()
    val yesterday = today.minusDays(1)
    // If date is more than a week old, use [Month] [Day of Month] format

    return when (date) {
        today -> "Today"
        yesterday -> "Yesterday"
        else -> date.format(DateTimeFormatter.ofPattern("EEEE"))
    }
}

private fun getLocalTime(timestamp: String): ZonedDateTime {
    val pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS][.SS][.S]XXX"
    val formatter = DateTimeFormatter.ofPattern(pattern)
    val utcTime = ZonedDateTime.parse(timestamp, formatter)
    val localTime = utcTime.withZoneSameInstant(ZoneId.systemDefault())
    return localTime
}