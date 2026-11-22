package co.edu.iub.campus_app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = CampusGreen,
    onPrimary = Color.White,
    primaryContainer = CampusSurfaceMuted,
    onPrimaryContainer = CampusInk,
    secondary = CampusTerracotta,
    onSecondary = Color.White,
    secondaryContainer = CampusTerracottaSoft,
    onSecondaryContainer = CampusTerracottaDark,
    tertiary = CampusBlue,
    onTertiary = Color.White,
    tertiaryContainer = CampusBlueSoft,
    onTertiaryContainer = CampusBlue,
    background = CampusCanvas,
    onBackground = CampusInk,
    surface = CampusSurface,
    onSurface = CampusInk,
    surfaceVariant = CampusSurfaceMuted,
    onSurfaceVariant = CampusInkMuted,
    outline = CampusBorder,
    outlineVariant = CampusSurfaceMuted,
    error = CampusRed,
    onError = Color.White,
    errorContainer = CampusRedSoft,
    onErrorContainer = CampusRed,
    scrim = CampusInk
)

@Composable
fun CampusTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = CampusTypography,
        shapes = CampusShapes,
        content = content
    )
}
