package com.example.yourgoldenhour.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.foundation.clickable
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.yourgoldenhour.R
import com.example.yourgoldenhour.ui.theme.YourGoldenHourTheme

@Composable
fun AddPhotoButton(modifier: Modifier = Modifier, onClick: () -> Unit = {}){
    val gradient = Brush.linearGradient(colors = listOf(MaterialTheme.colorScheme.secondary,
        MaterialTheme.colorScheme.primary))
    val shadowColor = MaterialTheme.colorScheme.secondary
    val strokeColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
    Column(modifier = modifier
        .fillMaxWidth()
        .clickable { onClick() }
        .aspectRatio(1.3f)
        .graphicsLayer{
            shadowElevation = 20.dp.toPx()
            shape = RoundedCornerShape(16.dp)
            clip = false
            spotShadowColor =  shadowColor
            ambientShadowColor = shadowColor
        }
        .background(brush = gradient,
            shape = RoundedCornerShape(16.dp))
        .drawBehind(){
            val strokeWidth = 2.dp.toPx()
            val dashWidth = 10.dp.toPx()
            val dashGap = 12.dp.toPx()

            val paddingInside = 25.dp.toPx()

            val stroke = Stroke(
                width = strokeWidth,
                cap = StrokeCap.Round,
                pathEffect = PathEffect.dashPathEffect(intervals = floatArrayOf(dashWidth, dashGap),
                    phase = 0f
                )
            )
            drawRoundRect(
                color = strokeColor,
                style = stroke,
                size = size.copy(
                    width = size.width - paddingInside * 2,
                    height = size.height - paddingInside * 2
                ),
                topLeft = Offset(paddingInside, paddingInside),
                cornerRadius = CornerRadius(16.dp.toPx())
            )

        },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Icon(painter = painterResource(id = R.drawable.camera_icon),
            contentDescription = "camera",
            tint = MaterialTheme.colorScheme.surface,
            modifier = Modifier.size(65.dp))
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Добавить фото",
            color = MaterialTheme.colorScheme.surface,
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

@Preview
@Composable
fun PreviewAddPhotoButton(){
    YourGoldenHourTheme {
        AddPhotoButton()
    }
}