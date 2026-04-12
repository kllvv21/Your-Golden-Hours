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
import com.example.yourgoldenhour.data.PhotoEntity


@Composable
fun GalleryScreen(
    photos: List<PhotoEntity>,
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
            listOf(),
            {},
            PaddingValues(0.dp)
        )
    }
}
