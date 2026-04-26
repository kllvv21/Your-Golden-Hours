package com.example.yourgoldenhour

import android.app.Application
import android.content.pm.PackageManager
import com.example.yourgoldenhour.api.ApiService
import com.example.yourgoldenhour.data.AppDatabase
import com.example.yourgoldenhour.repository.PhotoRepository
import com.yandex.mapkit.MapKitFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val FallbackMapKitApiKey = "f158d9a1-d3d7-4946-b2b9-5a91a2d7b162"

class GoldenHourApp : Application() {
    companion object {
        var isMapKitInitialized: Boolean = false
            private set
        var mapKitInitMessage: String = ""
            private set
    }

    override fun onCreate() {
        super.onCreate()
        val (apiKey, debugSource) = getMapKitApiKey()
        if (apiKey.isBlank()) {
            isMapKitInitialized = false
            mapKitInitMessage = "YANDEX_MAPKIT_API_KEY is empty ($debugSource)"
            return
        }

        isMapKitInitialized = try {
            MapKitFactory.setApiKey(apiKey)
            MapKitFactory.initialize(this)
            mapKitInitMessage = ""
            true
        } catch (e: Throwable) {
            mapKitInitMessage = e.message ?: e.javaClass.simpleName
            false
        }
    }

    private fun getMapKitApiKey(): Pair<String, String> {
        val fromBuildConfig = BuildConfig.YANDEX_MAPKIT_API_KEY
            .trim()
            .trim('"')
        if (fromBuildConfig.isNotBlank()) {
            return fromBuildConfig to "source=BuildConfig,len=${fromBuildConfig.length}"
        }

        return try {
            val appInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
            val fromManifest = appInfo.metaData
                ?.getString("com.yandex.maps.apikey")
                ?.trim()
                ?.trim('"')
                .orEmpty()
            val normalized = if (fromManifest.startsWith("\${")) "" else fromManifest
            if (normalized.isNotBlank()) {
                normalized to "source=Manifest,len=${normalized.length},pkg=$packageName"
            } else {
                FallbackMapKitApiKey to "source=FallbackConstant,len=${FallbackMapKitApiKey.length}"
            }
        } catch (_: Throwable) {
            FallbackMapKitApiKey to "source=FallbackConstant,len=${FallbackMapKitApiKey.length}"
        }
    }

    val database by lazy { AppDatabase.getDatabase(this) }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.sunrise-sunset.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    val repository by lazy {
        PhotoRepository(database.photoDao(), apiService)
    }
}