package com.example.yourgoldenhour.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.yourgoldenhour.R
import com.example.yourgoldenhour.ui.theme.YourGoldenHourTheme

@Composable
fun BottomMenuItem(
    iconRes: Int,
    description: String,
    isSelected: Boolean,
    route: String,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val iconColor = if (isSelected) {
        MaterialTheme.colorScheme.secondary
    } else {
        MaterialTheme.colorScheme.onBackground
    }
    IconButton(onClick = {onClick(route)}) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = description,
            tint = iconColor,
            modifier = modifier.size(35.dp)
        )
    }
}


@Composable
fun BottomMenu(currentScreen: String?,
               onNavigate: (String) -> Unit,
               modifier: Modifier = Modifier) {
    val shadowColor = MaterialTheme.colorScheme.secondary
    val backgroundColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)

    Box(
        modifier = modifier
            .padding(horizontal = 43.dp, vertical = 10.dp)
            .fillMaxWidth()
            .graphicsLayer {
                shadowElevation = 15.dp.toPx()
                shape = RoundedCornerShape(16.dp)
                clip = true
                spotShadowColor = shadowColor
                ambientShadowColor = shadowColor
            }
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 0.5.dp,
                color = Color.White.copy(alpha = 0.4f),
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp, vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomMenuItem(
                iconRes = R.drawable.image_icon,
                description = "gallery",
                isSelected = currentScreen == "gallery",
                route = "gallery",
                onClick = onNavigate
            )
            BottomMenuItem(
                iconRes = R.drawable.camera_icon,
                description = "camera",
                isSelected = currentScreen == "camera",
                route = "camera",
                onClick = onNavigate
            )
            BottomMenuItem(
                iconRes = R.drawable.map_icon,
                description = "map",
                isSelected = currentScreen == "map",
                route = "map",
                onClick = onNavigate
            )
        }
    }
}

@Preview
@Composable
fun PreviewBottom() {
    YourGoldenHourTheme {
        BottomMenu("map", {})
    }
}