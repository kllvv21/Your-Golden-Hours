package com.example.yourgoldenhour.screens

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.yourgoldenhour.components.GalleryCard
import com.example.yourgoldenhour.components.GalleryHeader
import androidx.compose.runtime.collectAsState
import com.example.yourgoldenhour.viewmodel.PhotoViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.example.yourgoldenhour.data.LocationState
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import java.util.Locale


@Composable
fun GalleryScreen(
    viewModel: PhotoViewModel,
    onCardClick: (Long) -> Unit,
    paddings: PaddingValues
) {
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val scope = rememberCoroutineScope()

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val isGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        if (isGranted) {
            scope.launch {
                viewModel.getUserLocation(context, fusedLocationClient)
            }
        }
    }

    LaunchedEffect(Unit) {
        val hasFineLocation = context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

        if (hasFineLocation) {
            viewModel.getUserLocation(context, fusedLocationClient)
        } else {

            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }
    val horizontalPaddings = Modifier.padding(horizontal = 50.dp)
    val photos by viewModel.allPhotos.collectAsState()

    val locationState = viewModel.locationInfo
    val sunTime = viewModel.goldenHourTime
    val userAddress = viewModel.userCurrentCity

    LaunchedEffect(locationState) {
        if (locationState is LocationState.Success){
            viewModel.loadSunTimes(locationState)
            viewModel.getUserAddress(context, locationState.latitude, locationState.longitude)
        }

    }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = paddings,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            GalleryHeader(time = sunTime, location = userAddress)
        }
        item {
            Text(
                text = "Твои золотые часы",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = horizontalPaddings
                    .fillMaxWidth()
            )
        }
        items(items = photos) { photo ->
            GalleryCard(
                photo = photo,
                onCardClick = onCardClick,
                modifier = horizontalPaddings
            )
        }
    }
}


//@Preview
//@Composable
//fun PreviewGalleryScreen() {
//    YourGoldenHourTheme {
//        GalleryScreen(
//            listOf(),
//            {},
//            PaddingValues(0.dp)
//        )
//    }
//}
