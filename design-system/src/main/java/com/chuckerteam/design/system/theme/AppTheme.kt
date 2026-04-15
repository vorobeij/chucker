package com.chuckerteam.design.system.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalContext

@Composable
public fun AppTheme(
    forcedDark: Boolean? = null,
    content: @Composable () -> Unit
) {
    val supportsDynamic = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    val inDarkMode: Boolean = isSystemInDarkTheme()
    val colorScheme: ColorScheme = when {
        forcedDark == true -> DarkThemeColors
        supportsDynamic -> {
            val context = LocalContext.current
            if (inDarkMode) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        else -> {
            if (inDarkMode) DarkThemeColors else LightThemeColors
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        shapes = shapes
    ) {
        content()
    }
}

public object AppTheme {
    /**
     * Retrieves the current [ColorScheme] at the call site's position in the hierarchy.
     *
     * @sample androidx.compose.material3.samples.ThemeColorSample
     */
    public val colorScheme: ColorScheme
        @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme

    /**
     * Retrieves the current [Typography] at the call site's position in the hierarchy.
     *
     * @sample androidx.compose.material3.samples.ThemeTextStyleSample
     */
    public val typography: Typography
        @Composable @ReadOnlyComposable get() = MaterialTheme.typography

    /**
     * Retrieves the current [Shapes] at the call site's position in the hierarchy.
     *
     * @sample androidx.compose.material3.samples.ThemeShapeSample
     */
    public val shapes: Shapes
        @Composable @ReadOnlyComposable get() = MaterialTheme.shapes
}

