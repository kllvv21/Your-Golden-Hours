package com.example.yourgoldenhour.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.yourgoldenhour.R
import com.example.yourgoldenhour.components.AddInputField
import com.example.yourgoldenhour.components.AddLocationField
import com.example.yourgoldenhour.components.AddPhotoButton
import com.example.yourgoldenhour.components.BlobBackground
import com.example.yourgoldenhour.components.SaveButton
import com.example.yourgoldenhour.components.ScreenHeader
import com.example.yourgoldenhour.ui.theme.YourGoldenHourTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.exifinterface.media.ExifInterface
import android.net.Uri
import coil.compose.AsyncImage
import com.example.yourgoldenhour.viewmodel.PhotoViewModel
import com.example.yourgoldenhour.data.PhotoEntity
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPhotoScreen(paddings:PaddingValues,
                   viewModel: PhotoViewModel? = null,
                   onBackClick: () -> Unit
                   ) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var photoUri by remember { mutableStateOf<String?>(null) }
    var detectedLatitude by remember { mutableStateOf(0.0) }
    var detectedLongitude by remember { mutableStateOf(0.0) }
    var photoDate by remember { mutableStateOf(LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM"))) }
    var photoTime by remember { mutableStateOf(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))) }
    var locationName by remember { mutableStateOf("Неизвестная локация") }
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

    val context = LocalContext.current
    val coroutineScope = androidx.compose.runtime.rememberCoroutineScope()
    
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        uri?.let {
            locationName = "Неизвестная локация"
            try {
                context.contentResolver.openInputStream(it)?.use { inputStream ->
                    val exif = ExifInterface(inputStream)
                    val latLong = exif.latLong
                    if (latLong != null) {
                        detectedLatitude = latLong[0]
                        detectedLongitude = latLong[1]
                        locationName = "Определение локации..."
                        
                        coroutineScope.launch(kotlinx.coroutines.Dispatchers.IO) {
                            try {
                                val geocoder = android.location.Geocoder(context, java.util.Locale.getDefault())
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                                    geocoder.getFromLocation(detectedLatitude, detectedLongitude, 1, object : android.location.Geocoder.GeocodeListener {
                                        override fun onGeocode(addresses: MutableList<android.location.Address>) {
                                            if (addresses.isNotEmpty()) {
                                                val address = addresses[0]
                                                val name = address.locality ?: address.subAdminArea ?: address.adminArea ?: address.countryName ?: "Неизвестная локация"
                                                locationName = name
                                            } else {
                                                locationName = "Локация не найдена"
                                            }
                                        }
                                        override fun onError(errorMessage: String?) {
                                            super.onError(errorMessage)
                                            locationName = "Способ определения недоступен"
                                        }
                                    })
                                } else {
                                    @Suppress("DEPRECATION")
                                    val addresses = geocoder.getFromLocation(detectedLatitude, detectedLongitude, 1)
                                    if (!addresses.isNullOrEmpty()) {
                                        val address = addresses[0]
                                        val name = address.featureName ?: address.locality ?: address.subAdminArea ?: address.adminArea ?: address.countryName ?: "Неизвестная локация"
                                        locationName = name
                                    } else {
                                        locationName = "Локация не найдена"
                                    }
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                                locationName = "Ошибка определения локации"
                            }
                        }
                    }

                    val dateTimeStr = exif.getAttribute(ExifInterface.TAG_DATETIME_ORIGINAL)
                    if (dateTimeStr != null) {
                        try {
                            val parts = dateTimeStr.split(" ")
                            if (parts.size == 2) {
                                val dateParts = parts[0].split(":")
                                if (dateParts.size == 3) {
                                    val parsedDate = LocalDate.of(dateParts[0].toInt(), dateParts[1].toInt(), dateParts[2].toInt())
                                    photoDate = parsedDate.format(DateTimeFormatter.ofPattern("dd MMMM"))
                                }
                                val timeParts = parts[1].split(":")
                                if (timeParts.size >= 2) {
                                    photoTime = "${timeParts[0]}:${timeParts[1]}"
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }

                val inpStream = context.contentResolver.openInputStream(it)
                if (inpStream != null) {
                    val file = File(context.filesDir, "photo_${System.currentTimeMillis()}.jpg")
                    val outStream = FileOutputStream(file)
                    inpStream.copyTo(outStream)
                    inpStream.close()
                    outStream.close()
                    photoUri = file.absolutePath
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    val horizontalPadding = Modifier.padding(horizontal = 25.dp)
    BlobBackground {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding =
                paddings,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                ScreenHeader(
                    title = "Добавить новое место",
                    onBackClick = onBackClick,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                )
            }
            item {
                if (photoUri == null) {
                    AddPhotoButton(
                        modifier = Modifier
                            .padding(horizontal = 60.dp, vertical = 20.dp),
                        onClick = {
                            launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                        }
                    )
                } else {
                    AsyncImage(
                        model = photoUri,
                        contentDescription = "Selected Photo",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(horizontal = 60.dp, vertical = 20.dp)
                            .fillMaxWidth()
                            .aspectRatio(1.3f)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                            }
                    )
                }
            }
            item {
                AddInputField(
                    title = "Название",
                    placeholder = "Введите название",
                    value = title,
                    onValueChange = { title = it },
                    singleLine = true,
                    modifier = horizontalPadding
                )
            }
            if (photoUri != null) {
                item {
                    AddInputField(
                        title = "Дата",
                        placeholder = "Дата съемки",
                        value = photoDate,
                        onValueChange = {},
                        readOnly = true,
                        onClick = { showDatePicker = true },
                        iconId = R.drawable.calendar_icon,
                        modifier = horizontalPadding
                    )
                }
                item {
                    AddInputField(
                        title = "Время",
                        placeholder = "Время съемки",
                        value = photoTime,
                        onValueChange = {},
                        readOnly = true,
                        onClick = { showTimePicker = true },
                        iconId = R.drawable.clock_icon,
                        modifier = horizontalPadding
                    )
                }
            }
            item {
                AddInputField(
                    title = "Описание",
                    placeholder = "Введите описание фотографии",
                    value = description,
                    onValueChange = { description = it },
                    modifier = horizontalPadding
                )
            }
            item {
                AddLocationField(
                    locationName = if (photoUri != null) locationName else "Выбрать точку на карте",
                    modifier = horizontalPadding.padding(top = 10.dp)
                )
            }
            item {
                SaveButton(
                    modifier = horizontalPadding.padding(vertical = 20.dp),
                    onClick = {
                        if (title.isNotBlank()) {
                            val entity = PhotoEntity(
                                date = photoDate,
                                time = photoTime,
                                title = title,
                                description = description,
                                location = locationName,
                                latitude = detectedLatitude,
                                longitude = detectedLongitude,
                                photoUri = photoUri ?: ""
                            )
                            viewModel?.insertPhoto(entity)
                            onBackClick()
                        }
                    }
                )
            }
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
fun PreviewAddPhotoScreen() {
    YourGoldenHourTheme {
        AddPhotoScreen(paddings = PaddingValues(0.dp),
            onBackClick = {})
    }
}