package com.example.yourgoldenhour.repository

import android.content.Context
import android.location.Geocoder
import com.example.yourgoldenhour.api.ApiService
import com.example.yourgoldenhour.data.PhotoDao
import com.example.yourgoldenhour.data.PhotoEntity
import com.example.yourgoldenhour.data.SunInfo
import com.example.yourgoldenhour.data.SunResponce
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale

class PhotoRepository(private val photoDao: PhotoDao, private val apiService: ApiService) {
    val allPhotos: Flow<List<PhotoEntity>> = photoDao.getAllPhotos()

    suspend fun insertPhoto(photo: PhotoEntity) {
        withContext(Dispatchers.IO) {
            photoDao.insertPhoto(photo)
        }
    }

    suspend fun updatePhoto(photo: PhotoEntity) {
        withContext(Dispatchers.IO) {
            photoDao.updatePhoto(photo)
        }
    }

    suspend fun deletePhoto(photo: PhotoEntity) {
        withContext(Dispatchers.IO) {
            photoDao.deletePhoto(photo)
        }
    }

    suspend fun fetchSunInfo(lat: Double, lng: Double, date: String): SunInfo {
        return apiService.getGoldenTime(lat, lng, date).results
    }


    suspend fun fetchCityName(context: Context, lat: Double, lng: Double): String = withContext(
        Dispatchers.IO
    ) {
        return@withContext try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(lat, lng, 1)
            addresses?.get(0)?.locality ?: "Неизвестное место"
        } catch (e: Exception) {
            "Координаты: $lat, $lng"
        }
    }

}
