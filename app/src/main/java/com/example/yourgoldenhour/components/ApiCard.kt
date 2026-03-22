package com.example.yourgoldenhour.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yourgoldenhour.R
import com.example.yourgoldenhour.ui.theme.YourGoldenHourTheme

@Composable
fun ApiCard(
    time: String,
    modifier: Modifier = Modifier
){
    val shadowColor = MaterialTheme.colorScheme.secondary
    val gradient = Brush.linearGradient(
        colors = listOf(MaterialTheme.colorScheme.secondary,MaterialTheme.colorScheme.primary))
    Card(modifier = modifier
        .fillMaxWidth()
        .background(brush = gradient, shape = RoundedCornerShape(16.dp))
        .graphicsLayer{
            shadowElevation = 20.dp.toPx()
            shape = RoundedCornerShape(16.dp)
            clip = false
            spotShadowColor = shadowColor
            ambientShadowColor = shadowColor
        },
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)){
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 9.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(9.dp)){
            Icon(
                painter = painterResource(R.drawable.sun_icon),
                contentDescription = "sun",
                tint = MaterialTheme.colorScheme.surface,
                modifier = Modifier.size(30.dp)
            )
            Text(text = "Время золотого часа",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.surface)

            Text(text = time,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.surface)

        }
    }
}

@Preview
@Composable
fun PreviewApiCard(){
    YourGoldenHourTheme {
        ApiCard("12:23 - 13:23")
    }

}