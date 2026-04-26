package com.example.yourgoldenhour.api

import com.example.yourgoldenhour.data.SunResponce
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("json")
    suspend fun getGoldenTime(@Query("lat") lat: Double,
                              @Query("lng") lng: Double,
                              @Query("date") date: String,
                              @Query("formatted") formatted: Int = 0
    ): SunResponce
}