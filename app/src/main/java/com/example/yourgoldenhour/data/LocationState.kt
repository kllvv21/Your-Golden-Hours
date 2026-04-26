package com.example.yourgoldenhour.data

sealed class LocationState {
    object Loading : LocationState()
    data class Success( val latitude: Double, val longitude: Double) : LocationState()
    data class Error(val message: String) : LocationState()
}