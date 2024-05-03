package com.example.treasurehunt.ui.mapfeature

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.treasurehunt.data.model.PoiItem
import com.example.treasurehunt.data.model.UserLocation
import com.example.treasurehunt.data.model.toLatLng
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun ComposeClosestPoiMap(poi: PoiItem, userLocation: UserLocation) {

    val cameraPositionState = rememberCameraPositionState()
    cameraPositionState.position = CameraPosition.fromLatLngZoom(poi.toLatLng(), 15F)


    GoogleMap(
        cameraPositionState = cameraPositionState,
        modifier = Modifier.fillMaxSize(),
        googleMapOptionsFactory = {
            GoogleMapOptions().apply {
                /*maxZoomPreference(20f)
                minZoomPreference(10f)*/
                Log.e("MapScreen", "UserLocation: $userLocation Poi: $poi")
                latLngBoundsForCameraTarget(
                    LatLngBounds.Builder()
                        .include(userLocation.toLatLng())
                        .include(poi.toLatLng())
                        .build()
                )
            }
        },
        properties = MapProperties(
            isMyLocationEnabled = true,
        )
    ) {
        poi?.let {
            Marker(
                state = MarkerState(poi.toLatLng()),
                title = poi.name,
                snippet = poi.description
            )
        }
    }
}
