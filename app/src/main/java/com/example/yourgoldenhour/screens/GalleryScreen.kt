package com.example.yourgoldenhour.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.yourgoldenhour.R
import com.example.yourgoldenhour.components.GalleryCard
import com.example.yourgoldenhour.components.GalleryHeader
import com.example.yourgoldenhour.ui.theme.YourGoldenHourTheme
import androidx.compose.foundation.lazy.items

data class Photo(
    val id: Int,
    val imageRes: Int,
    val title: String,
    val description: String,
    val location: String,
    val time: String,
    val date: String
)

val photoList = listOf(
    Photo(
        1,
        R.drawable.beauty,
        "Любимый закат",
        "Крутое описание",
        "Такая-то такая-то",
        "21:30",
        "15 июня"
    ),
    Photo(2, R.drawable.city, "Город", "Крутое описание", "Такая-то такая-то", "21:30", "15 июня"),
    Photo(3, R.drawable.third, "Горы", "Крутое описание", "Такая-то такая-то", "21:30", "15 июня"),
)


@Composable
fun GalleryScreen(
    photos: List<Photo>,
    onCardClick: (Int) -> Unit,
    paddings: PaddingValues
) {
    val horizontalPaddings = Modifier.padding(horizontal = 50.dp)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = paddings,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            GalleryHeader(time = "12:34 - 13:34", location = "Красноярск")
        }
        item {
            Text(
                text = "Твои золотые часы",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = horizontalPaddings
                    .fillMaxWidth()
            )
        }
        items(photos) { photo ->
            GalleryCard(
                photo = photo,
                onCardClick = onCardClick,
                modifier = horizontalPaddings
            )
        }
    }
}


@Preview
@Composable
fun PreviewGalleryScreen() {
    YourGoldenHourTheme {
        GalleryScreen(
            listOf(
                Photo(
                    1,
                    R.drawable.beauty,
                    "Любимый закат",
                    "Крутое описание",
                    "Такая-то такая-то",
                    "21:30",
                    "15 июня"
                ),
                Photo(
                    2,
                    R.drawable.city,
                    "Город",
                    "Крутое описание",
                    "Такая-то такая-то",
                    "21:30",
                    "15 июня"
                ),
                Photo(
                    3,
                    R.drawable.third,
                    "Горы",
                    "Крутое описание",
                    "Такая-то такая-то",
                    "21:30",
                    "15 июня"
                ),
            ),
            {},
            PaddingValues(0.dp)
        )
    }
}
