package com.example.yourgoldenhour.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.yourgoldenhour.R
import com.example.yourgoldenhour.screens.AddPhotoScreen
import com.example.yourgoldenhour.screens.EditScreen
import com.example.yourgoldenhour.screens.GalleryScreen
import com.example.yourgoldenhour.screens.MapScreen
import com.example.yourgoldenhour.screens.Photo
import com.example.yourgoldenhour.screens.PhotoScreen

val photoList = listOf(
    Photo(
        1,
        R.drawable.beauty,
        "Любимый закат",
        "Крутое описание",
        "Такая-то такая-то",
        "21:30",
        "15 июня"
    ),
    Photo(2, R.drawable.city, "Город", "Крутое описание", "Такая-то такая-то", "21:30", "15 июня"),
    Photo(3, R.drawable.third, "Горы", "Крутое описание", "Такая-то такая-то", "21:30", "15 июня"),
)

@Composable
fun AppNavigation(
    navController: NavHostController,
    innerPadding: PaddingValues,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Gallery.route,
    ) {
        composable(Screen.Gallery.route) {
            GalleryScreen(
                photos = photoList,
                onCardClick = { photoId ->
                    navController.navigate(Screen.PhotoScreen.createRoute(photoId))
                },
                paddings = innerPadding
            )
        }
        composable(Screen.Map.route) {
            MapScreen()
        }

        composable(Screen.AddPhotoScreen.route) {
            AddPhotoScreen(
                paddings = innerPadding,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(
            route = Screen.PhotoScreen.route,
            arguments = listOf(navArgument("photoId") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val photoId = backStackEntry.arguments?.getInt("photoId") ?: return@composable
            val photo = photoList.find { it.id == photoId } ?: photoList.first()
            PhotoScreen(
                photo = photo,
                onBackClick = { navController.popBackStack() },
                onRightClick = {
                    navController.navigate(
                        Screen.EditScreen.createRoute(
                            photoId
                        )
                    )
                },
                paddings = innerPadding
            )
        }
        composable(
            route = Screen.EditScreen.route,
            arguments = listOf(navArgument("photoId") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val photoId = backStackEntry.arguments?.getInt("photoId") ?: return@composable
            val photo = photoList.find { it.id == photoId } ?: photoList.first()
            EditScreen(
                photo = photo,
                onBackClick = { navController.popBackStack() },
                paddings = innerPadding
            )
        }
    }
}