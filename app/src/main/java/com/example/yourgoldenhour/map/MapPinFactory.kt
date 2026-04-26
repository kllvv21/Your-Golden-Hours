package com.example.yourgoldenhour.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PointF
import androidx.core.content.ContextCompat
import com.example.yourgoldenhour.R
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.RotationType

/**
 * MapKit often fails to show placemark icons from vector XML via [ImageProvider.fromResource].
 * Rasterize the vector drawable ourselves (Canvas) into a normal ARGB bitmap.
 */
fun rasterizedGradientPinBitmap(context: Context): Bitmap {
    val drawable = ContextCompat.getDrawable(context, R.drawable.gradient_pin)
        ?: error("gradient_pin drawable missing")
    val density = context.resources.displayMetrics.density
    val w = (31f * density).toInt().coerceIn(62, 256)
    val h = (44f * density).toInt().coerceIn(88, 360)
    val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
    bitmap.eraseColor(Color.TRANSPARENT)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, w, h)
    drawable.draw(canvas)
    return bitmap
}

/**
 * Billboard pin: tip at geo point.
 *
 * `IconStyle` full constructor order (MapKit 4.33): anchor, rotationType, **zIndex**, flat, visible, **scale**, tappableArea.
 * Passing `0f` in the scale slot made the icon invisible on all devices.
 */
fun mapPinIconStyle(): IconStyle = IconStyle(
    PointF(0.5f, 1f),
    RotationType.NO_ROTATION,
    null,
    false,
    true,
    1f,
    null,
)
