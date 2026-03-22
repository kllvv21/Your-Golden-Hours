package com.example.yourgoldenhour.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.yourgoldenhour.R
import com.example.yourgoldenhour.screens.PhotoScreen
import com.example.yourgoldenhour.ui.theme.YourGoldenHourTheme

@Composable
fun BlobBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Image(
            painter = painterResource(R.drawable.pink_blob),
            contentDescription = "blob",
            modifier
                .align(Alignment.TopEnd)
                .offset(x = 30.dp, y = (-37).dp)
                .rotate(24.25f)
                .alpha(0.8f)
        )

        Image(
            painter = painterResource(R.drawable.pink_blob),
            contentDescription = "blob",
            modifier
                .align(Alignment.BottomStart)
                .offset(x = (-10).dp, y = (10).dp)
//                .rotate(50f)
                .size(123.dp)
                .alpha(0.6f)
        )
        content()

    }
}


//@Preview
//@Composable
//fun PreviewBlobBachground() {
//    YourGoldenHourTheme {
//        BlobBackground {
//            PhotoScreen(photoId = R.drawable.city,
//                onBackClick = {},
//                paddings = PaddingValues(0.dp)
//            )
//        }
//    }
//}