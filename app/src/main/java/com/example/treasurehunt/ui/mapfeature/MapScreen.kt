package com.example.treasurehunt.ui.mapfeature

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.treasurehunt.Result
import com.example.treasurehunt.data.model.PoiItem

@Composable
fun MapScreen(mapViewModel: MapViewModel) {

    val pois = mapViewModel.pois.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        when (pois.value) {
            is Result.Success<*> -> {
                (pois.value as? Result.Success<List<PoiItem>>)?.let {
                    ComposeMap(it.data)
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