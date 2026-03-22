package com.example.yourgoldenhour.components


import androidx.compose.foundation.Image

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.rotate

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.yourgoldenhour.R
import com.example.yourgoldenhour.ui.theme.YourGoldenHourTheme


@Composable
fun GalleryHeader(
    time: String,
    location: String,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.clipToBounds()) {
        Image(
            painter = painterResource(R.drawable.pink_blob),
            contentDescription = "blob",
            modifier = Modifier
                .offset(x = (-70).dp, y = 150.dp)
                .rotate(65f),
            alignment = Alignment.BottomStart

        )
        Image(
            painter = painterResource(R.drawable.orange_blob),
            contentDescription = "blob",
            modifier = Modifier.offset(x = 326.dp, y = (-36).dp),
            alignment = Alignment.TopEnd
        )
        Column(
            modifier = Modifier.padding(horizontal = 40.dp, vertical = 20.dp)
        ) {
            HeaderTitle(location = location)
            Spacer(modifier = Modifier.height(15.dp))
            ApiCard(time = time)
        }
    }
}


@Preview
@Composable
fun PreviewGalleryHeader() {
    YourGoldenHourTheme {
        GalleryHeader(time = "12:34 - 13:34", location = "Красноярск")
    }

}