package com.example.yourgoldenhour.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.yourgoldenhour.R

import com.example.yourgoldenhour.ui.theme.YourGoldenHourTheme

@Composable
fun AddInputField(
    title: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    value: String = "",
    iconId: Int? = null,
    singleLine: Boolean = false
) {
    val shadowColor = MaterialTheme.colorScheme.secondary
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (iconId != null) {
                Icon(
                    painter = painterResource(id = iconId),
                    contentDescription = title,
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
            }
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        TextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    shadowElevation = 15.dp.toPx()
                    shape = RoundedCornerShape(16.dp)
                    clip = false
                    spotShadowColor = shadowColor
                    ambientShadowColor = shadowColor
                },
            placeholder = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            shape = RoundedCornerShape(16.dp),
            singleLine = singleLine,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                focusedTextColor = MaterialTheme.colorScheme.primary,
                focusedContainerColor = MaterialTheme.colorScheme.surface
            )

        )

    }

}

@Preview
@Composable
fun PreviewAddInputField() {
    YourGoldenHourTheme {
        AddInputField(title = "Название", placeholder = "Введите название", onValueChange = {})
    }
}

@Preview
@Composable
fun PreviewAddInputFieldIcon() {
    YourGoldenHourTheme {
        AddInputField(
            title = "Время",
            placeholder = "Введите время",
            onValueChange = {},
            iconId = R.drawable.clock_icon
        )
    }
}