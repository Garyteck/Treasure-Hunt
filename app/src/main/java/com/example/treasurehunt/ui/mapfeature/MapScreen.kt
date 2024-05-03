package com.example.treasurehunt.ui.mapfeature

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.treasurehunt.Result
import com.example.treasurehunt.data.model.PoiItem
import com.example.treasurehunt.data.model.UserLocation

@Composable
fun MapScreen(mapViewModel: MapViewModel) {

    val closestPoi = mapViewModel.closestPoiItem.collectAsState()
    val locationSource = mapViewModel.mapLocationSource
    val location = remember { mutableStateOf(UserLocation(0.0, 0.0)) }

    DisposableEffect(key1 = locationSource) {

        locationSource.activate { it ->
            location.value = UserLocation(it.latitude, it.longitude)
        }
        onDispose {
            locationSource.deactivate()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        when (closestPoi.value) {
            is Result.Success<*> -> {
                (closestPoi.value as? Result.Success<PoiItem?>)?.let {
                    ComposeClosestPoiMap(poi = it.data!!, userLocation = location.value)
                }
            }

            is Result.Error<*> -> {
                Text(text = "Error")
            }

            is Result.Loading<*> -> {
                CircularProgressIndicator()
            }
        }
    }

}