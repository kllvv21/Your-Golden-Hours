package com.example.yourgoldenhour.navigation

sealed class Screen(val route: String) {
    object Gallery : Screen("gallery")
    object Map : Screen("map")

    object AddPhotoScreen : Screen("camera")

    object PhotoScreen : Screen("photo_screen/{photoId}"){
        fun createRoute(photoId: Long): String = "photo_screen/$photoId"
    }

    object EditScreen : Screen("edit_screen/{photoId}"){
        fun createRoute(photoId: Long): String = "edit_screen/$photoId"
    }
}