package com.liquidglass.launcher.ui.components

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import com.liquidglass.launcher.ui.theme.GlassColors

/**
 * Frosted-glass surface: translucent tint + specular gradient border + soft shadow.
 * Blur is intentionally light — heavy blur hits the Mali-G52 (A14) too hard.
 */
@Composable
fun LiquidGlassSurface(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(28.dp),
    tint: Color = GlassColors.TintLight,
    blurContent: Dp = 0.dp,
    borderWidth: Dp = 1.dp,
    elevation: Dp = 8.dp,
    content: @Composable () -> Unit
) {
    val borderBrush = remember {
        Brush.verticalGradient(
            0f to GlassColors.BorderTop,
            0.5f to Color(0x22FFFFFF),
            1f to GlassColors.BorderBottom
        )
    }
    val sheenBrush = remember {
        Brush.verticalGradient(
            0f to Color(0x33FFFFFF),
            0.35f to Color(0x08FFFFFF),
            1f to Color(0x00FFFFFF)
        )
    }
    Box(
        modifier = modifier
            .shadow(elevation, shape, clip = false, ambientColor = GlassColors.Shadow, spotColor = GlassColors.Shadow)
            .clip(shape)
            .background(tint)
            .background(sheenBrush)
            .border(borderWidth, borderBrush, shape)
            .then(if (blurContent > 0.dp) Modifier.blur(blurContent) else Modifier)
    ) {
        content()
    }
}

/**
 * Drawable → Compose Image (needed for launcher icons pulled from PackageManager).
 */
@Composable
fun DrawableIcon(drawable: Drawable, modifier: Modifier = Modifier) {
    val bitmap = remember(drawable) {
        val w = drawable.intrinsicWidth.coerceAtLeast(96)
        val h = drawable.intrinsicHeight.coerceAtLeast(96)
        drawable.toBitmap(w, h).asImageBitmap()
    }
    Image(bitmap = bitmap, contentDescription = null, modifier = modifier)
}

/**
 * A single glass-cased app tile: icon + label under a soft glass halo.
 */
@Composable
fun AppTile(
    label: String,
    icon: Drawable,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    showLabel: Boolean = true,
    iconSize: Dp = 52.dp
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 6.dp, horizontal = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(iconSize + 12.dp)
                .shadow(6.dp, RoundedCornerShape(22.dp), clip = false, spotColor = Color(0x66000000))
                .clip(RoundedCornerShape(22.dp))
                .background(Color(0x1AFFFFFF))
                .border(1.dp, Brush.verticalGradient(
                    0f to Color(0x80FFFFFF),
                    1f to Color(0x22FFFFFF)
                ), RoundedCornerShape(22.dp)),
            contentAlignment = Alignment.Center
        ) {
            DrawableIcon(drawable = icon, modifier = Modifier.size(iconSize))
        }
        if (showLabel) {
            Spacer(Modifier.height(6.dp))
            Text(
                text = label,
                color = GlassColors.TextPrimary,
                fontSize = 11.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp)
            )
        }
    }
}
