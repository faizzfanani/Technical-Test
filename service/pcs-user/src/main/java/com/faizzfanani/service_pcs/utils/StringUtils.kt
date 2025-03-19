package com.faizzfanani.service_pcs.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun formatDate(isoDate: String): String {
    val instant = Instant.parse(isoDate)
    val formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy hh:mm a")
        .withZone(ZoneId.systemDefault()) // Adjust to your desired time zone
    return formatter.format(instant)
}