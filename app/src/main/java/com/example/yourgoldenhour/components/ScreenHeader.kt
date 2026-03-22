package com.example.yourgoldenhour.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.yourgoldenhour.R
import com.example.yourgoldenhour.ui.theme.YourGoldenHourTheme

@Composable
fun ScreenHeader(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    title: String? = null,
    rightIconRes: Int? = null,
    onRightClick: (() -> Unit)? = null
) {
    val shadowColor = MaterialTheme.colorScheme.secondary
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_left),
                contentDescription = "back",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(35.dp)
            )
        }
        if (title != null) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineMedium,
                maxLines = 1,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f).padding(horizontal = 8.dp)
            )

        }
        if (rightIconRes != null) {
            Surface(
                modifier = Modifier
                    .size(40.dp)
                    .graphicsLayer {
                        shadowElevation = 16.dp.toPx()
                        shape = CircleShape
                        clip = false
                        ambientShadowColor = shadowColor
                        spotShadowColor = shadowColor
                    },
                color = MaterialTheme.colorScheme.surface,
                shape = CircleShape
            ) {
                IconButton(onClick = { onRightClick?.invoke() }) {
                    Icon(
                        painter = painterResource(id = rightIconRes),
                        contentDescription = "back",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        } else {
            Spacer(
                modifier = Modifier.size(45.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewScreenHeader() {
    YourGoldenHourTheme {
        ScreenHeader(title = "Привет kdksdjgkj hkghdkgh khgkdhk hkjhgdfkhg kjhkfhg", onBackClick = {}, rightIconRes = R.drawable.pen_icon)
    }
}