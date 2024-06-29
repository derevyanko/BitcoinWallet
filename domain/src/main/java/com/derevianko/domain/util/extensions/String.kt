package com.derevianko.domain.util.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

fun String.parseDateTimeUTC(): Date? = try {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy HH:mm:ss z")
    dateFormat.timeZone = TimeZone.getTimeZone("UTC")
    dateFormat.parse(this)
} catch (e: Exception) {
    null
}

fun String.timeDifferenceInMinutes(date: Date = Date()): Long? {
    val parsedDate = this.parseDateTimeUTC() ?: return null

    val differenceInMillis = date.time - parsedDate.time

    val differenceInMinutes = differenceInMillis / (1000 * 60)
    return differenceInMinutes
}