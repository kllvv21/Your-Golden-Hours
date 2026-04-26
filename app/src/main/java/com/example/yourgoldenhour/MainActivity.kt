package com.example.yourgoldenhour

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.yourgoldenhour.components.BottomMenu
import com.example.yourgoldenhour.navigation.AppNavigation
import com.example.yourgoldenhour.navigation.Screen
import com.example.yourgoldenhour.ui.theme.YourGoldenHourTheme
import com.yandex.mapkit.MapKitFactory


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

    override fun onStart() {
        super.onStart()
        if (GoldenHourApp.isMapKitInitialized) {
            MapKitFactory.getInstance().onStart()
        }
    }

    override fun onStop() {
        if (GoldenHourApp.isMapKitInitialized) {
            MapKitFactory.getInstance().onStop()
        }
        super.onStop()
    }
}








