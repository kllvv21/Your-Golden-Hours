package com.example.yourgoldenhour.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.yourgoldenhour.R
import com.example.yourgoldenhour.screens.EditScreen
import com.example.yourgoldenhour.screens.Photo
import com.example.yourgoldenhour.ui.theme.YourGoldenHourTheme


@Composable
fun DialogButton(
    text: String,
    brush: Brush,
    contentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = Color.Transparent

    )
    {
        Box(
            modifier = Modifier.background(brush = brush).height(40.dp),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = text,
                color = contentColor,
                style = MaterialTheme.typography.labelMedium
            )
        }

    }
}

@Composable
fun DeleteCard(
    onDismiss: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shadowColor = MaterialTheme.colorScheme.secondary
    Dialog(onDismissRequest = onDismiss) {
        val gradient = Brush.linearGradient(
            listOf(
                MaterialTheme.colorScheme.primary,
                MaterialTheme.colorScheme.secondary
            )
        )
        Column(
            modifier = modifier
                .fillMaxWidth()
                .graphicsLayer {
                    shadowElevation = 15.dp.toPx()
                    shape = RoundedCornerShape(16.dp)
                    clip = false
                    spotShadowColor = shadowColor
                    ambientShadowColor = shadowColor
                }
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(top = 30.dp, bottom = 20.dp, start = 30.dp, end = 30.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Удаление фотографии",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Вы действительно хотите удалить эту фотографию?",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                textAlign = TextAlign.Center
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DialogButton(
                    text = "Удалить",
                    brush = SolidColor(MaterialTheme.colorScheme.onSurfaceVariant),
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f),
                    onClick = onDismiss)
                DialogButton(
                    text = "Отмена",
                    brush = gradient,
                    contentColor = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.weight(1f),
                    onClick = onDelete)
            }

        }
    }
}

@Preview
@Composable
fun PreviewDeleteCard() {
    YourGoldenHourTheme {
        EditScreen(Photo(1, R.drawable.beauty, "Любимый закат", "Крутое описание", "Такая-то такая-то", "21:30", "15 июня"),
            PaddingValues(0.dp),
            onBackClick = {},
        )
        DeleteCard({}, {})
    }
}


@Preview
@Composable
fun PreviewDeleteButton() {
    YourGoldenHourTheme {
        DialogButton(
            text = "Удалить",
            brush = SolidColor(MaterialTheme.colorScheme.onSurfaceVariant),
            contentColor = MaterialTheme.colorScheme.onSurface,
            onClick = {})
    }
}

