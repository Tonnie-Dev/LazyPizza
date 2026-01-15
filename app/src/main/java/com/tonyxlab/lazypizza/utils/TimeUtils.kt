@file:RequiresApi(Build.VERSION_CODES.O)

package com.tonyxlab.lazypizza.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.toFormattedDate(): String {
    val pattern = "MMMM d, HH:mm"
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return formatter.format(this)
}

fun LocalDateTime.toPickupTimeDisplayString(): String {

    val today = LocalDate.now()

    val pattern = if (toLocalDate() == today) {

        "HH:mm"
    } else {
        "MMMM d, HH:mm"
    }

    return DateTimeFormatter.ofPattern(pattern)
            .format(this)

}

fun formatSelectedPickupTime(date: LocalDate, time: LocalTime): String {
    val localDateTime = LocalDateTime.of(date, time)
    return localDateTime.toPickupTimeDisplayString()
}