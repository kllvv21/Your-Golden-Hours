package com.example.yourgoldenhour.components

import android.icu.text.CaseMap
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.yourgoldenhour.R
import com.example.yourgoldenhour.ui.theme.YourGoldenHourTheme


@Composable
fun InfoField(
    title: String,
    modifier: Modifier = Modifier,
    iconId: Int? = null,
    info: String? = null
) {
    if (info.isNullOrBlank()) return
    val shadowColor = MaterialTheme.colorScheme.secondary
    Surface(modifier = modifier.fillMaxWidth()
        .graphicsLayer{
            shadowElevation = 10.dp.toPx()
            shape = RoundedCornerShape(16.dp)
            clip = false
            spotShadowColor = shadowColor
            ambientShadowColor = shadowColor
        },
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface){
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            if (iconId != null) {
                Surface(
                    modifier = Modifier.size(30.dp), shape = CircleShape,
                    color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.25f)
                ) {
                    Box(contentAlignment = Alignment.Center){
                        Icon(
                            painter = painterResource(id = iconId),
                            contentDescription = title,
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(18.dp)

                        )

                    }


                }

            }
            Column(modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 10.dp),
                verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text(text = title, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurface)
                Text(text = info, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onBackground)
            }
        }
    }
}

@Preview
@Composable
fun PreviewInfoField(){
    YourGoldenHourTheme {
        InfoField(title = "Время", iconId = R.drawable.calendar_icon,  info = "19:21")
    }
}

@Preview
@Composable
fun PreviewInfoField2(){
    YourGoldenHourTheme {
        InfoField(title = "Описание",  info = "Какое-то описание тутутутутутутутутутут тутутутутутуту тутутуутт тутутутут туту ттуту")
    }
}