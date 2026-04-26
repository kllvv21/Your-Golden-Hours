package com.example.yourgoldenhour.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.yourgoldenhour.GoldenHourApp
import com.example.yourgoldenhour.components.LocationSearchBar
import com.example.yourgoldenhour.components.MapCardBubbleTail
import com.example.yourgoldenhour.components.MapCardLayout
import com.example.yourgoldenhour.components.MapInfoCard
import com.example.yourgoldenhour.data.PhotoEntity
import com.example.yourgoldenhour.map.mapPinIconStyle
import com.example.yourgoldenhour.map.rasterizedGradientPinBitmap
import com.example.yourgoldenhour.ui.theme.YourGoldenHourTheme
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import com.yandex.mapkit.geometry.Point as YandexPoint
import com.yandex.mapkit.map.Map as YandexMap
import android.graphics.PointF
import kotlin.math.roundToInt

private val DefaultMapLocation = YandexPoint(55.7558, 37.6173)
private const val DefaultZoom = 4f

/** True if we have any non-zero component; (0,0) means "no GPS" in this app. */
private fun PhotoEntity.hasStoredCoordinates(): Boolean =
    latitude != 0.0 || longitude != 0.0

@Composable
fun MapScreen(mapPhotos: List<PhotoEntity>) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val isPreview = LocalInspectionMode.current
    val groupedPhotos = remember(mapPhotos) {
        mapPhotos
            .filter { it.hasStoredCoordinates() }
            .groupBy { it.latitude to it.longitude }
    }
    var selectedPhotos by remember { mutableStateOf<List<PhotoEntity>?>(null) }
    /** MapWindow pixel coords of the selected pin (tip aligns with right bubble tail). */
    var selectedPinScreenPx by remember { mutableStateOf<Pair<Float, Float>?>(null) }
    var shouldRecenterMap by remember(groupedPhotos) { mutableStateOf(true) }

    Box(modifier = Modifier.fillMaxSize()) {
        if (isPreview) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Map preview is unavailable")
            }
        } else if (!GoldenHourApp.isMapKitInitialized) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                val reason = GoldenHourApp.mapKitInitMessage
                Text(
                    if (reason.isBlank()) {
                        "Yandex MapKit is not initialized. Check YANDEX_MAPKIT_API_KEY."
                    } else {
                        "Yandex MapKit init failed: $reason"
                    }
                )
            }
        } else {
            val mapView = remember { MapView(context) }
            val appContext = context.applicationContext
            val pinBitmap = remember(appContext) {
                rasterizedGradientPinBitmap(appContext)
            }
            val pinIconStyle = remember { mapPinIconStyle() }
            val tapListener = remember {
                MapObjectTapListener { mapObject, _ ->
                    @Suppress("UNCHECKED_CAST")
                    val tappedGroup = mapObject.userData as? List<PhotoEntity>
                    selectedPhotos = tappedGroup
                    true
                }
            }
            val mapInputListener = remember {
                object : InputListener {
                    override fun onMapTap(map: YandexMap, point: YandexPoint) {
                        selectedPhotos = null
                        selectedPinScreenPx = null
                    }

                    override fun onMapLongTap(map: YandexMap, point: YandexPoint) = Unit
                }
            }

            DisposableEffect(lifecycleOwner, mapView) {
                val observer = LifecycleEventObserver { _, event ->
                    when (event) {
                        Lifecycle.Event.ON_START -> {
                            MapKitFactory.getInstance().onStart()
                            mapView.onStart()
                        }
                        Lifecycle.Event.ON_STOP -> {
                            mapView.onStop()
                            MapKitFactory.getInstance().onStop()
                        }
                        else -> Unit
                    }
                }
                lifecycleOwner.lifecycle.addObserver(observer)
                onDispose {
                    lifecycleOwner.lifecycle.removeObserver(observer)
                    mapView.onStop()
                    MapKitFactory.getInstance().onStop()
                }
            }

            DisposableEffect(mapView, mapInputListener) {
                val map = mapView.mapWindow.map
                map.addInputListener(mapInputListener)
                onDispose {
                    map.removeInputListener(mapInputListener)
                }
            }

            LaunchedEffect(groupedPhotos) {
                shouldRecenterMap = true
            }

            LaunchedEffect(selectedPhotos, mapView) {
                val list = selectedPhotos
                if (list.isNullOrEmpty()) {
                    selectedPinScreenPx = null
                } else {
                    val geo = list.first()
                    val sp = mapView.mapWindow.worldToScreen(YandexPoint(geo.latitude, geo.longitude))
                    selectedPinScreenPx = sp?.let { it.x to it.y }
                }
            }

            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { mapView },
                update = { view ->
                    val map = view.mapWindow.map
                    map.mapObjects.clear()

                    val hiddenPinKey = selectedPhotos?.firstOrNull()?.let { it.latitude to it.longitude }
                    groupedPhotos.forEach { (coordinates, group) ->
                        if (coordinates == hiddenPinKey) return@forEach
                        val (lat, lon) = coordinates
                        val placemark = map.mapObjects.addPlacemark().apply {
                            geometry = YandexPoint(lat, lon)
                            setIcon(ImageProvider.fromBitmap(pinBitmap), pinIconStyle)
                            zIndex = 10_000f
                            setScaleFunction(
                                listOf(
                                    PointF(2f, 2.8f),
                                    PointF(6f, 1.8f),
                                    PointF(10f, 1.35f),
                                    PointF(14f, 1f),
                                    PointF(18f, 1.05f),
                                    PointF(22f, 1.15f),
                                )
                            )
                        }
                        placemark.userData = group
                        placemark.addTapListener(tapListener)
                    }

                    if (shouldRecenterMap) {
                        moveCameraToPhotos(map, groupedPhotos.keys.toList())
                        shouldRecenterMap = false
                    }

                    view.invalidate()
                }
            )
        }

        val photosForCard = selectedPhotos
        if (photosForCard != null && photosForCard.isNotEmpty()) {
            val configuration = LocalConfiguration.current
            val density = LocalDensity.current
            val popupOffset = run {
                val screenWpx = configuration.screenWidthDp * density.density
                val screenHpx = configuration.screenHeightDp * density.density
                val totalW = with(density) { MapCardLayout.BodyWidth.toPx() }
                val cardH = with(density) { MapCardLayout.EstimatedHeight.toPx() }
                val marginSide = with(density) { 8.dp.toPx() }
                val marginTop = with(density) { 100.dp.toPx() }
                val marginBottom = with(density) { 140.dp.toPx() }

                val anchor = selectedPinScreenPx
                if (anchor == null) {
                    IntOffset(
                        ((screenWpx - totalW) / 2f).roundToInt(),
                        ((screenHpx - cardH) / 2f).roundToInt(),
                    )
                } else {
                    val rawX = anchor.first - totalW
                    val rawY = anchor.second - cardH
                    val minX = marginSide
                    val maxX = (screenWpx - totalW - marginSide).coerceAtLeast(minX)
                    val minY = marginTop
                    val maxY = (screenHpx - cardH - marginBottom).coerceAtLeast(minY)
                    IntOffset(
                        rawX.roundToInt().coerceIn(minX.roundToInt(), maxX.roundToInt()),
                        rawY.roundToInt().coerceIn(minY.roundToInt(), maxY.roundToInt()),
                    )
                }
            }

            Popup(
                alignment = Alignment.TopStart,
                offset = popupOffset,
                onDismissRequest = {
                    selectedPhotos = null
                    selectedPinScreenPx = null
                },
                properties = PopupProperties(
                    focusable = false,
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true,
                    usePlatformDefaultWidth = false,
                ),
            ) {
                MapInfoCard(
                    photos = photosForCard,
                    bubbleTail = MapCardBubbleTail.Right,
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding()
                .padding(top = 10.dp)
        ) {
            LocationSearchBar(modifier = Modifier.padding(20.dp))
        }
    }
}

@Preview
@Composable
fun PreviewMapScreen() {
    YourGoldenHourTheme {
        MapScreen(emptyList())
    }
}

private fun moveCameraToPhotos(
    map: YandexMap,
    coordinates: List<Pair<Double, Double>>
) {
    if (coordinates.isEmpty()) {
        map.move(
            CameraPosition(DefaultMapLocation, DefaultZoom, 0f, 0f),
            Animation(Animation.Type.SMOOTH, 0.8f),
            null
        )
        return
    }

    if (coordinates.size == 1) {
        val (lat, lon) = coordinates.first()
        map.move(
            CameraPosition(YandexPoint(lat, lon), 14f, 0f, 0f),
            Animation(Animation.Type.SMOOTH, 0.8f),
            null
        )
        return
    }

    val minLat = coordinates.minOf { it.first }
    val maxLat = coordinates.maxOf { it.first }
    val minLon = coordinates.minOf { it.second }
    val maxLon = coordinates.maxOf { it.second }
    val center = YandexPoint((minLat + maxLat) / 2.0, (minLon + maxLon) / 2.0)
    val span = maxOf(maxLat - minLat, maxLon - minLon)
    val zoom = when {
        span > 30.0 -> 3.5f
        span > 10.0 -> 5.5f
        span > 3.0 -> 8f
        span > 1.0 -> 10f
        else -> 12f
    }

    map.move(
        CameraPosition(center, zoom, 0f, 0f),
        Animation(Animation.Type.SMOOTH, 0.8f),
        null
    )
}
