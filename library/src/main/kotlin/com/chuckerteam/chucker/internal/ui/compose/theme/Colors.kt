package com.chuckerteam.chucker.internal.ui.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

internal val LightThemeColors: ColorScheme = lightColorScheme(
    primary = Color(red = 0, green = 101, blue = 139),
    onPrimary = Color(red = 255, green = 255, blue = 255),
    primaryContainer = Color(red = 197, green = 231, blue = 255),
    onPrimaryContainer = Color(red = 0, green = 30, blue = 45),
    secondary = Color(red = 0, green = 106, blue = 97),
    onSecondary = Color(red = 255, green = 255, blue = 255),
    secondaryContainer = Color(red = 115, green = 248, blue = 231),
    onSecondaryContainer = Color(red = 0, green = 32, blue = 29),
    tertiary = Color(red = 108, green = 94, blue = 0),
    onTertiary = Color(red = 255, green = 255, blue = 255),
    tertiaryContainer = Color(red = 250, green = 227, blue = 102),
    onTertiaryContainer = Color(red = 33, green = 28, blue = 0),
    error = Color(red = 186, green = 26, blue = 26),
    onError = Color(red = 255, green = 255, blue = 255),
    errorContainer = Color(red = 255, green = 218, blue = 214),
    onErrorContainer = Color(red = 65, green = 0, blue = 2),
    outline = Color(red = 113, green = 120, blue = 126),
    background = Color(red = 251, green = 252, blue = 255),
    onBackground = Color(red = 25, green = 28, blue = 30),
    surface = Color(red = 249, green = 249, blue = 252),
    onSurface = Color(red = 25, green = 28, blue = 30),
    surfaceVariant = Color(red = 221, green = 227, blue = 234),
    onSurfaceVariant = Color(red = 65, green = 72, blue = 77),
    inverseSurface = Color(red = 46, green = 49, blue = 51),
    inverseOnSurface = Color(red = 240, green = 241, blue = 243),
    inversePrimary = Color(red = 126, green = 208, blue = 255),
    surfaceTint = Color(red = 0, green = 101, blue = 139),
    outlineVariant = Color(red = 193, green = 199, blue = 205),
    scrim = Color(red = 0, green = 0, blue = 0)
)
internal val DarkThemeColors: ColorScheme = darkColorScheme(
    primary = Color(red = 126, green = 208, blue = 255),
    onPrimary = Color(red = 0, green = 52, blue = 74),
    primaryContainer = Color(red = 0, green = 76, blue = 106),
    onPrimaryContainer = Color(red = 197, green = 231, blue = 255),
    secondary = Color(red = 82, green = 219, blue = 203),
    onSecondary = Color(red = 0, green = 55, blue = 50),
    secondaryContainer = Color(red = 0, green = 80, blue = 73),
    onSecondaryContainer = Color(red = 115, green = 248, blue = 231),
    tertiary = Color(red = 220, green = 199, blue = 77),
    onTertiary = Color(red = 56, green = 48, blue = 0),
    tertiaryContainer = Color(red = 81, green = 71, blue = 0),
    onTertiaryContainer = Color(red = 250, green = 227, blue = 102),
    error = Color(red = 255, green = 180, blue = 171),
    onError = Color(red = 105, green = 0, blue = 5),
    errorContainer = Color(red = 147, green = 0, blue = 10),
    onErrorContainer = Color(red = 255, green = 218, blue = 214),
    outline = Color(red = 139, green = 146, blue = 151),
    background = Color(red = 25, green = 28, blue = 30),
    onBackground = Color(red = 225, green = 226, blue = 229),
    surface = Color(red = 17, green = 20, blue = 22),
    onSurface = Color(red = 197, green = 198, blue = 201),
    surfaceVariant = Color(red = 65, green = 72, blue = 77),
    onSurfaceVariant = Color(red = 193, green = 199, blue = 205),
    inverseSurface = Color(red = 225, green = 226, blue = 229),
    inverseOnSurface = Color(red = 25, green = 28, blue = 30),
    inversePrimary = Color(red = 0, green = 101, blue = 139),
    surfaceTint = Color(red = 126, green = 208, blue = 255),
    outlineVariant = Color(red = 65, green = 72, blue = 77),
    scrim = Color(red = 0, green = 0, blue = 0)
)
internal val ColorScheme.neutral70: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) Color(0xFFAAABAE) else Color(0xFFAAABAE)
internal val ColorScheme.neutral50: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) Color(0xFF75777A) else Color(0xFF75777A)
