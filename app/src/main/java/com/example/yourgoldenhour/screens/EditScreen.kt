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
import androidx.compose.material3.TextButton
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.material3.Text
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.LocalTime
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
import com.example.yourgoldenhour.data.PhotoEntity
import com.example.yourgoldenhour.viewmodel.PhotoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(
    photo: PhotoEntity,
    paddings: PaddingValues,
    viewModel: PhotoViewModel? = null,
    onBackClick: () -> Unit,
    onDeleteComplete: () -> Unit = onBackClick
) {
    var showDeleteCard by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf(photo.title) }
    var photoDate by remember { mutableStateOf(photo.date) }
    var photoTime by remember { mutableStateOf(photo.time) }
    var locationName by remember { mutableStateOf(photo.location) }
    var description by remember { mutableStateOf(photo.description) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val year2000Millis = Instant.parse("2000-01-01T00:00:00Z").toEpochMilli()
                return utcTimeMillis in year2000Millis..System.currentTimeMillis()
            }
        }
    )
    val timePickerState = rememberTimePickerState(is24Hour = true)

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
                    photoUri = photo.photoUri,
                    modifier = Modifier.padding(horizontal = 45.dp, vertical = 10.dp)
                )
            }

            item {
                AddInputField(
                    title = "Название",
                    value = title,
                    placeholder = "Введите название",
                    onValueChange = { title = it },
                    modifier = horizontalPadding
                )
            }
            item {
                AddInputField(
                    title = "Дата",
                    iconId = R.drawable.calendar_icon,
                    value = photoDate,
                    placeholder = "Введите дату",
                    onValueChange = {},
                    readOnly = true,
                    onClick = { showDatePicker = true },
                    modifier = horizontalPadding
                )
            }
            item {
                AddInputField(
                    title = "Время",
                    iconId = R.drawable.clock_icon,
                    value = photoTime,
                    placeholder = "Введите время",
                    onValueChange = {},
                    readOnly = true,
                    onClick = { showTimePicker = true },
                    modifier = horizontalPadding
                )
            }

            item {
                AddInputField(
                    title = "Локация",
                    iconId = R.drawable.empty_pin_icon,
                    value = locationName,
                    placeholder = "Введите локацию",
                    onValueChange = { locationName = it },
                    modifier = horizontalPadding
                )
            }

            item {
                AddInputField(
                    title = "Описание",
                    value = description,
                    placeholder = "Введите описание",
                    onValueChange = { description = it },
                    modifier = horizontalPadding
                )
            }
            item {
                SaveButton(
                    modifier = horizontalPadding.padding(vertical = 20.dp),
                    onClick = {
                        val updatedPhoto = photo.copy(
                            title = title,
                            date = photoDate,
                            time = photoTime,
                            location = locationName,
                            description = description
                        )
                        viewModel?.updatePhoto(updatedPhoto)
                        onBackClick()
                    }
                )
            }
        }
        if (showDeleteCard) {
            DeleteCard(
                onDismiss = { showDeleteCard = false },
                onDelete = {
                    viewModel?.deletePhoto(photo)
                    showDeleteCard = false
                    onDeleteComplete()
                }
            )
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val date = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate()
                        photoDate = date.format(DateTimeFormatter.ofPattern("dd MMMM"))
                    }
                    showDatePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Отмена")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (showTimePicker) {
        AlertDialog(
            onDismissRequest = { showTimePicker = false },
            text = { TimePicker(state = timePickerState) },
            confirmButton = {
                TextButton(onClick = {
                    val time = LocalTime.of(timePickerState.hour, timePickerState.minute)
                    photoTime = time.format(DateTimeFormatter.ofPattern("HH:mm"))
                    showTimePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showTimePicker = false }) {
                    Text("Отмена")
                }
            }
        )
    }
}

@Preview
@Composable
fun PreviewEditScreen() {
    YourGoldenHourTheme {
        EditScreen(
            photo = PhotoEntity(1, "Такая-то такая-то", "21:30", "15 июня", "12", "", 0.0, 0.0, ""),
            paddings = PaddingValues(0.dp),
            onBackClick = {})
    }
}