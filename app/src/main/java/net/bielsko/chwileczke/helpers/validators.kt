package net.bielsko.chwileczke.helpers

import android.os.Build
import android.webkit.URLUtil
import androidx.annotation.RequiresApi
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun isValidUrl(url: String): Boolean {
    return URLUtil.isValidUrl(url)
}

@RequiresApi(Build.VERSION_CODES.O)
fun isValidTimeFormat(time: String): Boolean {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return try {
        val parsed = LocalTime.parse(time, formatter)
        // dodatkowo możesz wymusić zakres jeśli chcesz, np. od 00:00 do 23:59
        true
    } catch (e: DateTimeParseException) {
        false
    }
}