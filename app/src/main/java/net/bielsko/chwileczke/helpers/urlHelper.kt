package net.bielsko.chwileczke.helpers

import android.webkit.URLUtil

fun isValidUrl(url: String): Boolean {
    return URLUtil.isValidUrl(url)
}