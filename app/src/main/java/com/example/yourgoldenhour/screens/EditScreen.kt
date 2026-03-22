package com.example.yourgoldenhour.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.yourgoldenhour.R
import com.example.yourgoldenhour.components.AddInputField
import com.example.yourgoldenhour.components.BlobBackground
import com.example.yourgoldenhour.components.GoldenPhoto
import com.example.yourgoldenhour.components.SaveButton
import com.example.yourgoldenhour.components.ScreenHeader
import com.example.yourgoldenhour.ui.theme.YourGoldenHourTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.yourgoldenhour.components.DeleteCard

@Composable
fun EditScreen(
    photo: Photo,
    paddings: PaddingValues,
    onBackClick: () -> Unit,
) {
    var showDeleteCard by remember { mutableStateOf(false) }
    val horizontalPadding = Modifier.padding(horizontal = 25.dp)
    BlobBackground {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = paddings,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                ScreenHeader(
                    title = "Редактирование",
                    onBackClick = onBackClick,
                    rightIconRes = R.drawable.trash_icon,
                    onRightClick = { showDeleteCard = true },
                    modifier = horizontalPadding
                )
            }
            item {
                GoldenPhoto(
                    photoRes = photo.imageRes,
                    modifier = Modifier.padding(horizontal = 45.dp, vertical = 10.dp)
                )
            }

            item {
                AddInputField(
                    title = "Название",
                    value = photo.title,
                    placeholder = "Введите название",
                    onValueChange = {},
                    modifier = horizontalPadding
                )
            }
            item {
                AddInputField(
                    title = "Время",
                    iconId = R.drawable.clock_icon,
                    value = photo.time,
                    placeholder = "Введите время",
                    onValueChange = {},
                    modifier = horizontalPadding
                )
            }
            item {
                AddInputField(
                    title = "Дата",
                    iconId = R.drawable.calendar_icon,
                    value = photo.date,
                    placeholder = "Введите дату",
                    onValueChange = {},
                    modifier = horizontalPadding
                )
            }

            item {
                AddInputField(
                    title = "Локация",
                    iconId = R.drawable.empty_pin_icon,
                    value = photo.location,
                    placeholder = "Введите локацию",
                    onValueChange = {},
                    modifier = horizontalPadding
                )
            }

            item {
                AddInputField(
                    title = "Описание",
                    value = photo.description,
                    placeholder = "Введите описание",
                    onValueChange = {},
                    modifier = horizontalPadding
                )
            }
            item {
                SaveButton(
                    modifier = horizontalPadding.padding(vertical = 20.dp)
                )
            }
        }
        if (showDeleteCard) {
            DeleteCard(
                onDismiss = { showDeleteCard = false },
                onDelete = {
                    showDeleteCard = false
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewEditScreen() {
    YourGoldenHourTheme {
        EditScreen(
            photo = Photo(
                1,
                R.drawable.beauty,
                "Любимый закат",
                "Крутое описание",
                "Такая-то такая-то",
                "21:30",
                "15 июня"
            ),
            paddings = PaddingValues(0.dp),
            onBackClick = {})
    }
}