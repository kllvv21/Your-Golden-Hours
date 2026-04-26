package com.example.yourgoldenhour.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.yourgoldenhour.screens.AddPhotoScreen
import com.example.yourgoldenhour.screens.EditScreen
import com.example.yourgoldenhour.screens.GalleryScreen
import com.example.yourgoldenhour.screens.MapScreen
import com.example.yourgoldenhour.screens.PhotoScreen
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yourgoldenhour.viewmodel.PhotoViewModel
import com.example.yourgoldenhour.viewmodel.PhotoViewModelFactory
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.ui.unit.dp
import com.example.yourgoldenhour.GoldenHourApp
import com.google.android.gms.location.LocationServices

@Composable
fun AppNavigation(
    navController: NavHostController,
    innerPadding: PaddingValues,
) {
    val context = LocalContext.current
    val app = context.applicationContext as GoldenHourApp

    val photoViewModel: PhotoViewModel = viewModel(
        factory = PhotoViewModelFactory(app.repository)
    )

    val allPhotos by photoViewModel.allPhotos.collectAsState()

    val layoutDirection = LocalLayoutDirection.current
    val customPadding = PaddingValues(
        start = innerPadding.calculateStartPadding(layoutDirection),
        top = innerPadding.calculateTopPadding(),
        end = innerPadding.calculateEndPadding(layoutDirection),
        bottom = innerPadding.calculateBottomPadding() + 30.dp
    )

    NavHost(
        navController = navController,
        startDestination = Screen.Gallery.route,
    ) {
        composable(Screen.Gallery.route) {
            GalleryScreen(
                viewModel = photoViewModel,
                onCardClick = { photoId ->
                    navController.navigate(Screen.PhotoScreen.createRoute(photoId))
                },
                paddings = customPadding
            )
        }
        composable(Screen.Map.route) {
            MapScreen(mapPhotos = allPhotos)
        }

        composable(Screen.AddPhotoScreen.route) {
            AddPhotoScreen(
                paddings = customPadding,
                viewModel = photoViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(
            route = Screen.PhotoScreen.route,
            arguments = listOf(navArgument("photoId") { type = NavType.LongType })
        ) { backStackEntry ->
            val photoId = backStackEntry.arguments?.getLong("photoId") ?: 0L
            val photo = allPhotos.find { it.id == photoId }

            if (photo != null) {
                PhotoScreen(
                    photo = photo,
                    onBackClick = { navController.popBackStack() },
                    onRightClick = {
                        navController.navigate(Screen.EditScreen.createRoute(photoId))
                    },
                    paddings = customPadding
                )
            }
        }
        composable(
            route = Screen.EditScreen.route,
            arguments = listOf(navArgument("photoId") { type = NavType.LongType })
        ) { backStackEntry ->
            val photoId = backStackEntry.arguments?.getLong("photoId") ?: 0L
            val photo = allPhotos.find { it.id == photoId }

            if (photo != null) {
                EditScreen(
                    photo = photo,
                    viewModel = photoViewModel,
                    onBackClick = { navController.popBackStack() },
                    onDeleteComplete = {
                        navController.popBackStack(
                            Screen.Gallery.route,
                            inclusive = false,
                            saveState = false,
                        )
                    },
                    paddings = customPadding
                )
            }
        }
    }
}