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

@Composable
fun CompassScreen(compassViewModel: CompassViewModel) {

    val direction = compassViewModel.direction.collectAsState()
    val distance = compassViewModel.distance.collectAsState()

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
            Compass(direction, distance)
        }

    }
}

