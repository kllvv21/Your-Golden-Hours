package com.example.yourgoldenhour.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.yourgoldenhour.R
import com.example.yourgoldenhour.components.BottomMenu
import com.example.yourgoldenhour.components.LocationSearchBar
import com.example.yourgoldenhour.components.MapInfoCard
import com.example.yourgoldenhour.navigation.photoList
import com.example.yourgoldenhour.ui.theme.YourGoldenHourTheme

@Composable
fun MapScreen() {
    val searchPaddings = Modifier.padding(20.dp)

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.map_image),
            contentDescription = "map",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        MapInfoCard(photoList, modifier = Modifier.align(Alignment.Center))
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding()
                .padding(top = 10.dp)
        ) {
            LocationSearchBar(modifier = searchPaddings)
        }
    }
}


@Preview
@Composable
fun PreviewMapScreen() {
    YourGoldenHourTheme {
        MapScreen()
    }

}