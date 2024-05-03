package com.example.treasurehunt.ui.compass

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
import com.example.treasurehunt.ui.poi.PoiSelector
import com.example.treasurehunt.ui.poi.TakePictureViewModel

@Composable
fun CompassScreen(compassViewModel: CompassViewModel, takePictureViewModel: TakePictureViewModel) {

    val direction = compassViewModel.direction.collectAsState()
    val distance = compassViewModel.distance.collectAsState()
    val closestPoi = compassViewModel.closestPoiItem.collectAsState()
    val isButtonEnabled = takePictureViewModel.isButtonEnabled.collectAsState()
    val selectPoi = { it : Result<PoiItem?> -> takePictureViewModel.selectPoi(it) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (direction.value is Result.Loading<*> && distance.value is Result.Loading<*>) {
            CircularProgressIndicator()
        } else if (direction.value is Result.Error<*> && distance.value is Result.Error<*>) {
            Text(text = "Error cannot load direction nor distance")
        } else {
            Text(text = "closestPoint: ${when(closestPoi.value) {
                is Result.Success -> (closestPoi.value as Result.Success).data?.name + ((closestPoi.value as Result.Success).data?.description)
                is Result.Error -> "Error"
                is Result.Loading -> "LOADING..."
            }}")
            Compass(direction, distance)
            PoiSelector(isButtonEnabled, closestPoi, selectPoi)
        }

    }
}

