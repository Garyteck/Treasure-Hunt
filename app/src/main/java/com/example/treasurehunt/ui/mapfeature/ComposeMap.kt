package com.example.treasurehunt.ui.mapfeature

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.treasurehunt.data.model.PoiItem
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@Composable
fun ComposeMap(pois: List<PoiItem>) {

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
    ) {
        pois.forEach { poi ->
            Marker(
                state = MarkerState(poi.toLatLng()),
                title = poi.name,
                snippet = poi.description
            )
        }
    }
}