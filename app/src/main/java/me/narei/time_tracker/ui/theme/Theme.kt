package me.narei.time_tracker.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryGreen,
    onPrimary = Color.White,
    secondary = SecondaryGreen,
    onSecondary = Color.White,
    onBackground = TextTitleDark,
    onSurface = TextTitleDark,
    onSurfaceVariant = TextBodyDark,
    error = ErrorDark,
    onError = Color.White,
    outline = BorderDark
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryGreen,
    onPrimary = Color.White,
    secondary = SecondaryGreen,
    onSecondary = Color.White,
    background = Color.White,
    onBackground = TextTitle,
    surface = Color.White,
    onSurface = TextTitle,
    onSurfaceVariant = TextBody,
    error = Error,
    onError = ErrorText,
    outline = Border
)

@Composable
fun TimeTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    CompositionLocalProvider(
        LocalSpacing provides Spacing()
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content,
            shapes = Shape
        )
    }
}