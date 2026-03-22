package com.example.yourgoldenhour

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.yourgoldenhour.components.BlobBackground
import com.example.yourgoldenhour.components.BottomMenu
import com.example.yourgoldenhour.components.DeleteCard
import com.example.yourgoldenhour.navigation.AppNavigation
import com.example.yourgoldenhour.navigation.Screen
import com.example.yourgoldenhour.screens.AddPhotoScreen
import com.example.yourgoldenhour.screens.EditScreen
import com.example.yourgoldenhour.screens.GalleryScreen
import com.example.yourgoldenhour.screens.MapScreen
import com.example.yourgoldenhour.screens.PhotoScreen
import com.example.yourgoldenhour.ui.theme.YourGoldenHourTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YourGoldenHourTheme {
                CompositionLocalProvider(
                    LocalOverscrollFactory provides null
                ) {
                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route

                    Scaffold(
                        bottomBar = {
                            if (currentRoute == Screen.Gallery.route || currentRoute == Screen.Map.route || currentRoute == Screen.AddPhotoScreen.route) {
                                BottomMenu(
                                    currentScreen = currentRoute,
                                    onNavigate = { route ->
                                        navController.navigate(route) {
                                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }
                    ) { innerPadding ->
                        AppNavigation(
                            navController = navController,
                            innerPadding = innerPadding
                        )
                    }
                }
            }
        }
    }
}








