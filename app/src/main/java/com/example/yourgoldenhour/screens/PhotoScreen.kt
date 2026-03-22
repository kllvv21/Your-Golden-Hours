package com.example.yourgoldenhour.screens

import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.yourgoldenhour.R
import com.example.yourgoldenhour.components.BlobBackground
import com.example.yourgoldenhour.components.BottomMenu
import com.example.yourgoldenhour.components.GoldenPhoto
import com.example.yourgoldenhour.components.InfoField
import com.example.yourgoldenhour.components.ScreenHeader
import com.example.yourgoldenhour.ui.theme.YourGoldenHourTheme

@Composable
fun PhotoScreen(
    photo: Photo,
    onBackClick: () -> Unit,
    onRightClick: () -> Unit,
    paddings: PaddingValues
) {
    val horizontalPadding = Modifier.padding(horizontal = 25.dp)

    BlobBackground {
        LazyColumn(
            contentPadding = paddings,
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                ScreenHeader(
                    onBackClick = onBackClick,
                    rightIconRes = R.drawable.pen_icon,
                    onRightClick = onRightClick,
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
                )
            }
            item {
                GoldenPhoto(
                    photoRes = photo.imageRes,
                    modifier = Modifier.padding(horizontal = 45.dp)
                )
            }
            item {
                Text(
                    text = photo.title,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = horizontalPadding
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                )
            }
            item {
                InfoField(
                    title = "Время",
                    iconId = R.drawable.clock_icon,
                    info = photo.time,
                    modifier = horizontalPadding
                )
            }
            item {
                InfoField(
                    title = "Дата",
                    iconId = R.drawable.calendar_icon,
                    info = photo.date,
                    modifier = horizontalPadding
                )
            }
            item {
                InfoField(
                    title = "Локация",
                    iconId = R.drawable.empty_pin_icon,
                    info = photo.location,
                    modifier = horizontalPadding
                )
            }
            item {
                InfoField(
                    title = "Описание",
                    info = photo.description,
                    modifier = horizontalPadding
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewPhotoScreen() {
    YourGoldenHourTheme {
        PhotoScreen(
            photo = Photo(
                1,
                R.drawable.beauty,
                "Любимый закат",
                "Крутое описание",
                "Такая-то такая-то",
                "21:30",
                "15 июня"
            ), onBackClick = {},
            onRightClick = {},
            paddings = PaddingValues(0.dp)
        )
    }
}