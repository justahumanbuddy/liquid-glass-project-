package com.liquidglass.launcher.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object GlassColors {
    val TintLight = Color(0x33FFFFFF)
    val TintMid = Color(0x22FFFFFF)
    val TintDark = Color(0x22000000)
    val BorderTop = Color(0xB3FFFFFF)
    val BorderBottom = Color(0x33FFFFFF)
    val Specular = Color(0xFFFFFFFF)
    val TextPrimary = Color(0xFFFFFFFF)
    val TextSecondary = Color(0xCCFFFFFF)
    val Shadow = Color(0x66000000)
}

private val LiquidDarkScheme = darkColorScheme(
    primary = Color(0xFFB6D4FF),
    onPrimary = Color(0xFF001F3F),
    surface = Color(0x1AFFFFFF),
    onSurface = Color(0xFFFFFFFF),
    background = Color.Transparent,
    onBackground = Color(0xFFFFFFFF)
)

private val LiquidTypography = Typography(
    displayLarge = TextStyle(fontSize = 44.sp, fontWeight = FontWeight.Light),
    headlineMedium = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Medium),
    titleLarge = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold),
    bodyLarge = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Normal),
    labelSmall = TextStyle(fontSize = 11.sp, fontWeight = FontWeight.Medium)
)

@Composable
fun LiquidGlassTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LiquidDarkScheme,
        typography = LiquidTypography,
        content = content
    )
}
