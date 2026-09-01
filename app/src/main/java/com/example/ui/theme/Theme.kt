package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = BotanicalGreen,
    onPrimary = PureWhite,
    primaryContainer = LightBotanicalGreen,
    onPrimaryContainer = DarkForestGreen,
    secondary = SecondaryGreen,
    onSecondary = PureWhite,
    secondaryContainer = SoftBeige,
    onSecondaryContainer = TextPrimary,
    tertiary = GoldAccent,
    onTertiary = PureWhite,
    background = WarmCream,
    onBackground = TextPrimary,
    surface = PureWhite,
    onSurface = TextPrimary,
    surfaceVariant = SoftBeige,
    onSurfaceVariant = TextSecondary,
    outline = BorderLight,
    outlineVariant = BorderSubtle,
    error = ErrorRed,
    onError = PureWhite
)

private val DarkColorScheme = darkColorScheme(
    primary = LightBotanicalGreen,
    onPrimary = DarkForestGreen,
    primaryContainer = BotanicalGreen,
    onPrimaryContainer = PureWhite,
    secondary = SageGreen,
    onSecondary = DarkForestGreen,
    background = DarkForestGreen,
    onBackground = WarmCream,
    surface = Color(0xFF26472A),
    onSurface = WarmCream,
    surfaceVariant = Color(0xFF1B351F),
    onSurfaceVariant = Color(0xFFC7D3C3),
    outline = BorderDark,
    outlineVariant = Color(0xFF2B4A2F)
)

@Composable
fun VerdantPureTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
