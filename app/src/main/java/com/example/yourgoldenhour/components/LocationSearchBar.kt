package com.example.yourgoldenhour.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.yourgoldenhour.R

import com.example.yourgoldenhour.ui.theme.YourGoldenHourTheme

@Composable
fun LocationSearchBar(modifier: Modifier = Modifier) {
    var query by remember { mutableStateOf("") }
    val shadowColor = MaterialTheme.colorScheme.secondary
    TextField(
        value = query,
        onValueChange = {newText -> query = newText},
        modifier = modifier.fillMaxWidth()
            .graphicsLayer{
                shadowElevation = 16.dp.toPx()
                shape = RoundedCornerShape(16.dp)
                clip = false
                spotShadowColor = shadowColor
                ambientShadowColor = shadowColor
            },
        placeholder = {
            Text(
                text = "Найти место", style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.magnifier),
                contentDescription = "search",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(30.dp)
            )
        },
        trailingIcon = {
            if (!query.isEmpty()) {
                IconButton(onClick = {query = ""}){
                    Icon(
                        painter = painterResource(R.drawable.x),
                        contentDescription = "delete query",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
        },
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surface.copy(0.8f),
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedTextColor = MaterialTheme.colorScheme.primary,
            focusedContainerColor = MaterialTheme.colorScheme.surface.copy(0.8f)
        ),
        maxLines = 1
    )

}

@Preview
@Composable
fun PreviewLocationSearchBar() {
    YourGoldenHourTheme {
        LocationSearchBar()
    }
}