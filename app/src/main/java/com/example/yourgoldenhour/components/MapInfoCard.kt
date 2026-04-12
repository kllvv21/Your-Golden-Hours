package com.example.yourgoldenhour.components

import coil.compose.AsyncImage
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.yourgoldenhour.R
import com.example.yourgoldenhour.data.PhotoEntity
import com.example.yourgoldenhour.ui.theme.YourGoldenHourTheme

class MapBubbleShape(
    private val cornerRadius: Dp = 16.dp,
    private val arrowHeight: Dp = 20.dp
) : Shape {
    override fun createOutline(
        size: androidx.compose.ui.geometry.Size,
        layoutDirection: LayoutDirection,
        density: Density
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

@Composable
fun MapInfoCard(
    photos: List<PhotoEntity>,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { photos.size })

    val currentPhoto = photos[pagerState.currentPage]

    val bubbleShape = MapBubbleShape()

    Column(
        modifier = modifier
            .width(250.dp)
            .graphicsLayer {
                shadowElevation = 12.dp.toPx()
                shape = bubbleShape
                clip = true
                spotShadowColor = Color.Black.copy(alpha = 0.25f)
            }
            .background(Color.White)
    ) {
        Box(modifier = Modifier.fillMaxWidth().height(150.dp)) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                AsyncImage(
                    model = photos[page].photoUri.takeIf { it.isNotBlank() },
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 20.dp, top = 12.dp, bottom = 35.dp), // Отступ под хвостик
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = currentPhoto.title, // Берем заголовок текущего фото
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = currentPhoto.time, // Берем время текущего фото
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFDEEE9)
@Composable
fun PreviewMapInfoCard() {
    val testPhotos = listOf(
        PhotoEntity(1, "Такая-то такая-то", "19:21", "Дата", "Закат", "", 0.0, 0.0, ""),
        PhotoEntity(2, "Такая-то такая-то", "21:00", "Дата", "Город", "", 0.0, 0.0, "")
    )

    YourGoldenHourTheme {
        Box(modifier = Modifier.fillMaxSize().padding(20.dp), contentAlignment = Alignment.Center) {
            MapInfoCard(photos = testPhotos)
        }
    }
}