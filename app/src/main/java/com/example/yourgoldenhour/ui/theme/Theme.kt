package com.example.yourgoldenhour.ui.theme


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable



private val LightColorScheme = lightColorScheme(
    primary = Pink,
    secondary = Orange,
    tertiary = Yellow,
    background = BGColorLight,
    surface = White,
    onPrimary = White,
    onBackground = MainTextLight,
    onSurface = TextLight,
    onSurfaceVariant = LightGray
)

@Composable
fun YourGoldenHourTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}