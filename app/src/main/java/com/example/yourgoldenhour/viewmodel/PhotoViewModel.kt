package com.example.yourgoldenhour.viewmodel

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourgoldenhour.data.LocationState
import com.example.yourgoldenhour.data.PhotoEntity
import com.example.yourgoldenhour.repository.PhotoRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeoutOrNull
import retrofit2.HttpException
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class PhotoViewModel(private val repository: PhotoRepository) : ViewModel() {

    var goldenHourTime by mutableStateOf("Загрузка...")
        private set
    var locationInfo by mutableStateOf<LocationState>(LocationState.Loading)
        private set

    var userCurrentCity by mutableStateOf<String>("Загрузка...")
    val allPhotos: StateFlow<List<PhotoEntity>> = repository.allPhotos
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun insertPhoto(photo: PhotoEntity) = viewModelScope.launch {
        repository.insertPhoto(photo)
    }

    fun updatePhoto(photo: PhotoEntity) = viewModelScope.launch {
        repository.updatePhoto(photo)
    }

    fun deletePhoto(photo: PhotoEntity) = viewModelScope.launch {
        repository.deletePhoto(photo)
    }


    fun loadSunTimes(location: LocationState) {
        if (location !is LocationState.Success) return
        viewModelScope.launch {
            try {
                val lat = location.latitude
                val lng = location.longitude
                val now = LocalTime.now()
                val results = repository.fetchSunInfo(lat, lng, LocalDate.now().toString())
                val sunrise =
                    ZonedDateTime.parse(results.sunrise).withZoneSameInstant(ZoneId.systemDefault())
                        .toLocalTime()
                val sunset =
                    ZonedDateTime.parse(results.sunset).withZoneSameInstant(ZoneId.systemDefault())
                        .toLocalTime()
                val formatter = DateTimeFormatter.ofPattern("HH:mm")

                goldenHourTime = when {
                    now.isBefore(sunrise.plusHours(1)) -> {
                        val end = sunrise.plusHours(1)
                        "${sunrise.format(formatter)} - ${end.format(formatter)}"
                    }

                    now.isBefore(sunset) -> {
                        val start = sunset.minusHours(1)
                        "${start.format(formatter)} - ${sunset.format(formatter)}"
                    }
                    else -> {
                        val tomorrowDate = LocalDate.now().plusDays(1).toString()
                        val tomorrowResults = repository.fetchSunInfo(lat, lng, tomorrowDate)
                        val tomorrowSunrise = ZonedDateTime.parse(tomorrowResults.sunrise).withZoneSameInstant(ZoneId.systemDefault())
                            .toLocalTime()
                        "${tomorrowSunrise.format(formatter)} - ${tomorrowSunrise.plusHours(1).format(formatter)}"
                    }
                }

            } catch (e: java.net.SocketTimeoutException) {
                goldenHourTime = "Сервер долго не отвечает"
            } catch (e: java.net.UnknownHostException) {
                goldenHourTime = "Нет подключения к сети"
            } catch (e: Exception) {
                goldenHourTime = "Что-то пошло не так"
            }
        }
    }

    suspend fun getUserLocation(context: Context, locationClient: FusedLocationProviderClient) {
        locationInfo = LocationState.Loading

        if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            try {
                val freshLocation = withTimeoutOrNull(5000L) {
                    locationClient.getCurrentLocation(
                        Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                        CancellationTokenSource().token
                    ).await()
                }

                if (freshLocation != null) {
                    locationInfo =
                        LocationState.Success(freshLocation.latitude, freshLocation.longitude)
                } else {
                    val lastLocation = locationClient.lastLocation.await()
                    locationInfo = if (lastLocation != null) {
                        LocationState.Success(lastLocation.latitude, lastLocation.longitude)
                    } else {
                        LocationState.Error("Местоположение не найдено (Таймаут)")
                    }
                }
            } catch (e: Exception) {
                val errorName = e.javaClass.simpleName
                val errorMsg = e.message
                goldenHourTime = "Ошибка: $errorName"
            }
        } else {
            println("DEBUG: Нет разрешений")
            locationInfo = LocationState.Error("Нет разрешений")
        }
    }

    fun getUserAddress(context: Context, lat: Double, lng: Double) {
        viewModelScope.launch {
            userCurrentCity = repository.fetchCityName(context, lat, lng)
        }


    }
}

