package com.faizzfanani.core_ui.text_style

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import id.faizzfanani.technical_test.core_ui.R

val customSans = FontFamily(
    Font(R.font.custom_sans_light, FontWeight.Light),
    Font(R.font.custom_sans, FontWeight.Normal),
    Font(R.font.custom_sans_medium, FontWeight.Medium),
    Font(R.font.custom_sans_bold, FontWeight.Bold),
    Font(R.font.custom_sans_light_italic, FontWeight.Light, FontStyle.Italic),
)

val pageHeaderStyle = TextStyle(
    fontSize = 18.sp,
    fontFamily = customSans,
    fontWeight = FontWeight.Bold,
)

val titleStyle = TextStyle(
    fontSize = 14.sp,
    fontFamily = customSans,
    fontWeight = FontWeight.Bold,
)

val bodyLightStyle = TextStyle(
    fontSize = 12.sp,
    fontFamily = customSans,
    fontWeight = FontWeight.Light
)

val bodyRegularStyle = TextStyle(
    fontSize = 12.sp,
    fontFamily = customSans,
    fontWeight = FontWeight.Normal
)

val bodyMediumStyle = TextStyle(
    fontSize = 12.sp,
    fontFamily = customSans,
    fontWeight = FontWeight.Medium
)

val bodyBoldStyle = TextStyle(
    fontSize = 12.sp,
    fontFamily = customSans,
    fontWeight = FontWeight.Bold
)

val bodyItalicStyle = TextStyle(
    fontSize = 12.sp,
    fontFamily = customSans,
    fontWeight = FontWeight.Light,
    fontStyle = FontStyle.Italic
)