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
import com.example.yourgoldenhour.screens.PhotoScreen
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yourgoldenhour.data.AppDatabase
import com.example.yourgoldenhour.repository.PhotoRepository
import com.example.yourgoldenhour.viewmodel.PhotoViewModel
import com.example.yourgoldenhour.viewmodel.PhotoViewModelFactory


@Composable
fun AppNavigation(
    navController: NavHostController,
    innerPadding: PaddingValues,
) {
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)
    val repository = PhotoRepository(database.photoDao())
    val photoViewModel: PhotoViewModel = viewModel(factory = PhotoViewModelFactory(repository))
    val allPhotos by photoViewModel.allPhotos.collectAsState(initial = emptyList())

    NavHost(
        navController = navController,
        startDestination = Screen.Gallery.route,
    ) {
        composable(Screen.Gallery.route) {
            GalleryScreen(
                photos = allPhotos,
                onCardClick = { photoId ->
                    navController.navigate(Screen.PhotoScreen.createRoute(photoId))
                },
                paddings = innerPadding
            )
        }
        composable(Screen.Map.route) {
            MapScreen(mapPhotos = allPhotos)
        }

        composable(Screen.AddPhotoScreen.route) {
            AddPhotoScreen(
                paddings = innerPadding,
                viewModel = photoViewModel,
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
            val photo = allPhotos.find { it.id == photoId } ?: return@composable
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
            val photo = allPhotos.find { it.id == photoId } ?: return@composable
            EditScreen(
                photo = photo,
                viewModel = photoViewModel,
                onBackClick = { navController.popBackStack() },
                onDeleteComplete = { 
                    navController.popBackStack(Screen.Gallery.route, inclusive = false) 
                },
                paddings = innerPadding
            )
        }
    }
}