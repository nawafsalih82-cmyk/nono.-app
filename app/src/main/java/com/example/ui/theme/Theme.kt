package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
  primary = EmeraldGreenPrimary,
  onPrimary = Color.White,
  primaryContainer = EmeraldGreenContainer,
  onPrimaryContainer = OnEmeraldGreenContainer,
  secondary = GoldBright,
  onSecondary = Color.White,
  secondaryContainer = GoldContainer,
  onSecondaryContainer = OnGoldContainer,
  tertiary = RoseAccent,
  onTertiary = Color.White,
  tertiaryContainer = RoseContainer,
  onTertiaryContainer = OnRoseContainer,
  background = OffWhiteBg,
  onBackground = TextDark,
  surface = PureWhiteSurface,
  onSurface = TextDark,
  surfaceVariant = Color(0xFFF1F5F9),
  onSurfaceVariant = TextMuted,
  outline = CardBorderColor,
  error = WrongRed,
  onError = Color.White,
  errorContainer = WrongRedLight,
  onErrorContainer = WrongRed,
)

private val DarkColorScheme = darkColorScheme(
  primary = EmeraldGreenLight,
  onPrimary = Color.Black,
  primaryContainer = EmeraldGreenDark,
  onPrimaryContainer = EmeraldGreenContainer,
  secondary = GoldBright,
  onSecondary = Color.Black,
  secondaryContainer = OnGoldContainer,
  onSecondaryContainer = GoldContainer,
  tertiary = RoseAccent,
  onTertiary = Color.White,
  tertiaryContainer = OnRoseContainer,
  onTertiaryContainer = RoseContainer,
  background = DarkBg,
  onBackground = DarkText,
  surface = DarkSurface,
  onSurface = DarkText,
  surfaceVariant = Color(0xFF334155),
  onSurfaceVariant = Color(0xFF94A3B8),
  outline = DarkCardBorder,
  error = WrongRed,
  onError = Color.White,
  errorContainer = Color(0xFF7F1D1D),
  onErrorContainer = WrongRedLight,
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // We prefer our curated quiz palette for strong branding consistency
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}
