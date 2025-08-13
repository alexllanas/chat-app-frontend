package com.chatappfrontend.common.util

import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter

import java.time.ZoneId
import java.time.ZonedDateTime

fun formatCurrentTime() = formatMessageTime(Instant.now().toString())

fun formatMessageTime(timestamp: String): String {
    println("message timestamp: $timestamp")
    val localTime = getLocalTime(timestamp)
    val outputFormatter = DateTimeFormatter.ofPattern("h:mm a")
    return localTime.format(outputFormatter)
}


/**
 * TODO:
 * - within the minute "Now"
 * - within the day (midnight to present) -> "1:12 PM" e.g.
 * - before a year -> 12/01/2010
 */
fun formatChatDate(timestamp: String): String {
    val localTime = getLocalTime(timestamp)
    val date = localTime.toLocalDate()
    val today = LocalDate.now()
    val yesterday = today.minusDays(1)
    val lastWeek = today.minusDays(7)
    val lastYear = today.minusYears(1)

    return when {
        date == today -> "Today"
        date == yesterday -> "Yesterday"
        date.isBefore(lastYear) -> date.format(DateTimeFormatter.ofPattern("MMM d, yyyy"))
        date.isBefore(lastWeek) -> date.format(DateTimeFormatter.ofPattern("MMM d"))
        else -> date.format(DateTimeFormatter.ofPattern("EEEE"))
    }
}

private fun getLocalTime(timestamp: String): ZonedDateTime {
    val pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSSSSS][.SSSSS][.SSSS][.SSS][.SS][.S]XXX"
    val formatter = DateTimeFormatter.ofPattern(pattern)
    val utcTime = ZonedDateTime.parse(timestamp, formatter)
    val localTime = utcTime.withZoneSameInstant(ZoneId.systemDefault())
    return localTime
}