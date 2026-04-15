package com.chuckerteam.chucker.internal.ui.compose.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.unit.sp
import com.chuckerteam.chucker.R

private val fontRegular = Font(resId = R.font.roboto_regular).toFontFamily()
private val fontMedium = Font(resId = R.font.roboto_medium).toFontFamily()
internal val typography = Typography(
    displayLarge = TextStyle(
        fontFamily = fontMedium,
        fontSize = 57.sp
    ),
    displayMedium = TextStyle(
        fontFamily = fontMedium,
        fontSize = 45.sp
    ),
    displaySmall = TextStyle(
        fontFamily = fontMedium,
        fontSize = 36.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = fontRegular,
        fontSize = 32.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = fontRegular,
        fontSize = 28.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = fontRegular,
        fontSize = 24.sp
    ),
    titleLarge = TextStyle(
        fontFamily = fontRegular,
        fontSize = 22.sp
    ),
    titleMedium = TextStyle(
        fontFamily = fontMedium,
        fontSize = 16.sp
    ),
    titleSmall = TextStyle(
        fontFamily = fontMedium,
        fontSize = 14.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = fontRegular,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = fontRegular,
        fontSize = 14.sp
    ),
    bodySmall = TextStyle(
        fontFamily = fontRegular,
        fontSize = 12.sp
    ),
    labelLarge = TextStyle(
        fontFamily = fontMedium,
        fontSize = 14.sp
    ),
    labelMedium = TextStyle(
        fontFamily = fontMedium,
        fontSize = 12.sp
    ),
    labelSmall = TextStyle(
        fontFamily = fontMedium,
        fontSize = 11.sp
    )
)
