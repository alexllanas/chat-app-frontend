package com.chatappfrontend.common

import java.time.format.DateTimeFormatter

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

fun convertToMessageTime(timestamp: String): String {
    val pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS][.SS][.S]XXX"
    val formatter = DateTimeFormatter.ofPattern(pattern)
    val utcTime = ZonedDateTime.parse(timestamp, formatter)
    val localTime = utcTime.withZoneSameInstant(ZoneId.systemDefault())
    val outputFormatter = DateTimeFormatter.ofPattern("h:mm a")
    return localTime.format(outputFormatter)
}