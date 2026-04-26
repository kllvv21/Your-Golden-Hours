package com.example.yourgoldenhour.components

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

/** Bubble with arrow at the bottom center (points down). */
class MapBubbleShapeBottom(
    private val cornerRadius: Dp = 16.dp,
    private val arrowHeight: Dp = 20.dp,
) : Shape {
    override fun createOutline(
        size: androidx.compose.ui.geometry.Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        val path = Path().apply {
            val r = with(density) { cornerRadius.toPx() }
            val aH = with(density) { arrowHeight.toPx() }
            val w = size.width
            val h = size.height

            moveTo(r, 0f)
            lineTo(w - r, 0f)
            arcTo(Rect(w - 2 * r, 0f, w, 2 * r), 270f, 90f, false)
            lineTo(w, h - aH - r)
            lineTo(w, h)
            lineTo(w - r * 1.5f, h - aH)
            lineTo(r, h - aH)
            arcTo(Rect(0f, h - aH - 2 * r, 2 * r, h - aH), 90f, 90f, false)
            lineTo(0f, r)
            arcTo(Rect(0f, 0f, 2 * r, 2 * r), 180f, 90f, false)
            close()
        }
        return Outline.Generic(path)
    }
}

/**
 * Like [MapBubbleShapeBottom], but the downward tail sits at the **bottom-right** (tip near `(width, height)`).
 */
class MapBubbleShapeBottomRightTail(
    private val cornerRadius: Dp = 16.dp,
    private val arrowHeight: Dp = 20.dp,
    private val arrowBaseWidth: Dp = 36.dp,
) : Shape {
    override fun createOutline(
        size: androidx.compose.ui.geometry.Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        val path = Path().apply {
            val r = with(density) { cornerRadius.toPx() }
            val aH = with(density) { arrowHeight.toPx() }
            val w = size.width
            val h = size.height
            val aw = with(density) { arrowBaseWidth.toPx() }.coerceIn(r * 1.2f, (w - 3f * r).coerceAtLeast(r * 2.5f))

            moveTo(r, 0f)
            lineTo(w - r, 0f)
            arcTo(Rect(w - 2 * r, 0f, w, 2 * r), 270f, 90f, false)
            lineTo(w, h - aH - r)
            lineTo(w, h)
            lineTo(w - aw, h - aH)
            lineTo(r, h - aH)
            arcTo(Rect(0f, h - aH - 2 * r, 2 * r, h - aH), 90f, 90f, false)
            lineTo(0f, r)
            arcTo(Rect(0f, 0f, 2 * r, 2 * r), 180f, 90f, false)
            close()
        }
        return Outline.Generic(path)
    }
}

enum class MapCardBubbleTail {
    Bottom,
    Right,
}
