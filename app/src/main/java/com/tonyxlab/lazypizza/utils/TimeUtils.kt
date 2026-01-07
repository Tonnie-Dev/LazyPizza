
@file:RequiresApi(Build.VERSION_CODES.O)

package com.tonyxlab.lazypizza.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


fun LocalDateTime.toFormattedDate(): String {
    val pattern = "MMMM d, HH:mm"
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return formatter.format(this)
}

fun LocalDateTime.calculateEarliestPickupTime(): String{

    val earliest = plusMinutes(15L)
    val pattern = "HH:mm"
    return DateTimeFormatter.ofPattern(pattern).format(earliest)
}
