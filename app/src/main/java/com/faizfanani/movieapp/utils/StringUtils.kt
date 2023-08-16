package com.faizfanani.movieapp.utils

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Moh.Faiz Fanani on 01/08/2023
 */
object StringUtils {

    fun formatStringToDate(dateStr: String, style: String, locale: Locale? = Locale.ENGLISH): Date? {
        return try {
            val formatter = SimpleDateFormat(style, locale)
            formatter.parse(dateStr)
        } catch (e: Exception) {
            null
        }
    }

    fun formatDateTime(
        dateTime: Date?,
        style: String,
        local: Locale? = null
    ): String {
        return try {
            dateTime?.let {
                val formatter = SimpleDateFormat(style, local ?: Locale.ENGLISH)
                formatter.format(dateTime)
            } ?: return ""
        } catch (e: Exception) {
            ""
        }
    }

    fun formatThousandIndonesian(value: String, defaultValue: String = ""): String {
        val number = value.toLongOrNull() ?: return defaultValue
        var result = NumberFormat.getNumberInstance(Locale.US).format(number)
        val token = StringTokenizer(result, ".")
        result = token.nextToken()
        result = result.replace(",", ".")
        return result
    }
}