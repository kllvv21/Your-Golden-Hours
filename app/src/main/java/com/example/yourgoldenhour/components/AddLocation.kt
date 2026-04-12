package com.example.yourgoldenhour.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.yourgoldenhour.R
import com.example.yourgoldenhour.ui.theme.YourGoldenHourTheme

@Composable
fun AddLocationField(locationName: String = "Выбрать точку на карте", modifier: Modifier = Modifier){
    val pinColor = MaterialTheme.colorScheme.secondary
    Column(modifier = modifier.fillMaxWidth()
        .graphicsLayer{
            shadowElevation = 10.dp.toPx()
            clip = false
            shape = RoundedCornerShape(16.dp)
            spotShadowColor = pinColor
            ambientShadowColor = pinColor
        }
        .background(color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(16.dp))
        .padding(all = 15.dp)


        ) {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically){
            Box(contentAlignment = Alignment.Center){
                Canvas(modifier = Modifier.size(26.dp)) {
                    drawCircle(color = pinColor.copy(alpha = 0.25f))
                }
                Icon( painter = painterResource( id = R.drawable.empty_pin_icon),
                    contentDescription = "location",
                    tint = pinColor,
                    modifier = Modifier.size(18.dp)
                )

            }
            Text(text = locationName, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)


        }

    }

}

@Preview
@Composable
fun PreviewAddLocationField(){
    YourGoldenHourTheme {
        AddLocationField()
    }
}