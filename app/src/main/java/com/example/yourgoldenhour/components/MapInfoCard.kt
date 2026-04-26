package com.example.yourgoldenhour.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.yourgoldenhour.data.PhotoEntity
import com.example.yourgoldenhour.ui.theme.YourGoldenHourTheme

/** Layout constants for map popup (pin = bottom-right tip of bubble, i.e. near `width × height`). */
object MapCardLayout {
    val BodyWidth: Dp = 250.dp
    val EstimatedHeight: Dp = 230.dp
}

@Composable
fun MapInfoCard(
    photos: List<PhotoEntity>,
    modifier: Modifier = Modifier,
    bubbleTail: MapCardBubbleTail = MapCardBubbleTail.Bottom,
) {
    val pagerState = rememberPagerState(pageCount = { photos.size })
    val currentPhoto = photos[pagerState.currentPage]

    when (bubbleTail) {
        MapCardBubbleTail.Bottom -> MapInfoCardBottomTail(
            photos = photos,
            pagerState = pagerState,
            currentPhoto = currentPhoto,
            modifier = modifier,
        )
        MapCardBubbleTail.Right -> MapInfoCardRightTail(
            photos = photos,
            pagerState = pagerState,
            currentPhoto = currentPhoto,
            modifier = modifier,
        )
    }
}

@Composable
private fun MapInfoCardBottomTail(
    photos: List<PhotoEntity>,
    pagerState: PagerState,
    currentPhoto: PhotoEntity,
    modifier: Modifier,
) {
    val bubbleShape = MapBubbleShapeBottom()
    Column(
        modifier = modifier
            .width(MapCardLayout.BodyWidth)
            .graphicsLayer {
                shadowElevation = 12.dp.toPx()
                shape = bubbleShape
                clip = true
                spotShadowColor = Color.Black.copy(alpha = 0.25f)
            }
            .background(Color.White),
    ) {
        MapInfoCardImagePager(photos = photos, pagerState = pagerState)
        MapInfoCardTextRow(
            currentPhoto = currentPhoto,
            padding = PaddingValues(start = 16.dp, end = 20.dp, top = 12.dp, bottom = 35.dp),
        )
    }
}

@Composable
private fun MapInfoCardRightTail(
    photos: List<PhotoEntity>,
    pagerState: PagerState,
    currentPhoto: PhotoEntity,
    modifier: Modifier,
) {
    val bubbleShape = MapBubbleShapeBottomRightTail()
    Column(
        modifier = modifier
            .width(MapCardLayout.BodyWidth)
            .graphicsLayer {
                shadowElevation = 12.dp.toPx()
                shape = bubbleShape
                clip = true
                spotShadowColor = Color.Black.copy(alpha = 0.25f)
            }
            .background(Color.White),
    ) {
        MapInfoCardImagePager(photos = photos, pagerState = pagerState)
        MapInfoCardTextRow(
            currentPhoto = currentPhoto,
            padding = PaddingValues(start = 16.dp, end = 20.dp, top = 12.dp, bottom = 35.dp),
        )
    }
}

@Composable
private fun MapInfoCardImagePager(
    photos: List<PhotoEntity>,
    pagerState: PagerState,
) {
    Box(modifier = Modifier.fillMaxWidth().height(150.dp)) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
        ) { page ->
            AsyncImage(
                model = photos[page].photoUri.takeIf { it.isNotBlank() },
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
        }
    }
}

@Composable
private fun MapInfoCardTextRow(
    currentPhoto: PhotoEntity,
    padding: PaddingValues,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = currentPhoto.title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Text(
            text = currentPhoto.time,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.secondary,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFDEEE9)
@Composable
fun PreviewMapInfoCard() {
    val testPhotos = listOf(
        PhotoEntity(1, "Такая-то такая-то", "19:21", "Дата", "Закат", "", 0.0, 0.0, ""),
        PhotoEntity(2, "Такая-то такая-то", "21:00", "Дата", "Город", "", 0.0, 0.0, ""),
    )

    YourGoldenHourTheme {
        Box(modifier = Modifier.fillMaxSize().padding(20.dp), contentAlignment = Alignment.Center) {
            MapInfoCard(photos = testPhotos)
        }
    }
}
