package com.tabtab.tv.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/** Available accent colors the user can pick in Settings — all pass ≥4.5:1 contrast on dark/AMOLED. */
enum class AccentColor(val color: Color) {
    AMBER(Color(0xFFF59E0B)),
    CYAN(Color(0xFF06B6D4)),
    VIOLET(Color(0xFF8B5CF6)),
    EMERALD(Color(0xFF10B981)),
    ROSE(Color(0xFFF43F5E))
}

enum class AppThemeMode { DARK, LIGHT, AMOLED }

// Off-white instead of pure white on dark/AMOLED backgrounds — reduces eye strain on TV.
private val TextOnDark = Color(0xFFF2F2F2)
private val TextSecondaryOnDark = Color(0xFFA0A0A0)

private fun darkScheme(accent: Color) = darkColorScheme(
    primary = accent,
    background = Color(0xFF0A0A0A),
    surface = Color(0xFF161616),
    onPrimary = Color.Black,
    onBackground = TextOnDark,
    onSurface = TextOnDark,
    onSurfaceVariant = TextSecondaryOnDark
)

private fun amoledScheme(accent: Color) = darkColorScheme(
    primary = accent,
    background = Color(0xFF000000),
    surface = Color(0xFF0A0A0A),
    onPrimary = Color.Black,
    onBackground = TextOnDark,
    onSurface = TextOnDark,
    onSurfaceVariant = TextSecondaryOnDark
)

private fun lightScheme(accent: Color) = lightColorScheme(
    primary = accent,
    background = Color(0xFFFAFAFA),
    surface = Color(0xFFFFFFFF),
    onPrimary = Color.White,
    onBackground = Color(0xFF111111),
    onSurface = Color(0xFF111111),
    onSurfaceVariant = Color(0xFF555555)
)

@Composable
fun TabTabTvTheme(
    themeMode: AppThemeMode = AppThemeMode.DARK,
    accent: AccentColor = AccentColor.AMBER,
    isTv: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when (themeMode) {
        AppThemeMode.DARK -> darkScheme(accent.color)
        AppThemeMode.LIGHT -> lightScheme(accent.color)
        AppThemeMode.AMOLED -> amoledScheme(accent.color)
    }
    MaterialTheme(colorScheme = colorScheme, typography = tabTabTypography(isTv), content = content)
}
