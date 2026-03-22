package com.example.yourgoldenhour.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.yourgoldenhour.ui.theme.YourGoldenHourTheme

@Composable
fun SaveButton(modifier: Modifier = Modifier){
    val shadowColor = MaterialTheme.colorScheme.secondary
    val gradient = Brush.linearGradient(colors = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.secondary))
    Column(modifier = modifier
        .fillMaxWidth()
        .graphicsLayer{
            shadowElevation = 15.dp.toPx()
            shape = RoundedCornerShape(16.dp)
            clip = true
            spotShadowColor = shadowColor
            ambientShadowColor = shadowColor
        }
        .background(brush = gradient,
            shape = RoundedCornerShape(16.dp))
        .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Сохранить",
            color = MaterialTheme.colorScheme.surface,
            style = MaterialTheme.typography.headlineMedium
            )

    }
}

@Preview
@Composable
fun PreviewSaveButton(){
    YourGoldenHourTheme {
        SaveButton()
    }
}