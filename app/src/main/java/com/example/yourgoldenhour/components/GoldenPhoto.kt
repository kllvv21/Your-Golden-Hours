package com.example.yourgoldenhour.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.yourgoldenhour.R
import com.example.yourgoldenhour.ui.theme.YourGoldenHourTheme

@Composable
fun GoldenPhoto(photoRes: Int,
                modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Image(
            painter = painterResource(photoRes),
            contentDescription = "golden photo",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()

        )
    }
}

@Preview
@Composable
fun PreviewGoldenPhoto() {
    YourGoldenHourTheme {
        GoldenPhoto(R.drawable.city)
    }
}