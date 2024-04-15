package com.example.treasurehunt.ui.compass

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.treasurehunt.Result

@Composable
fun Compass(
    direction: State<Result<Double?>>,
    distance: State<Result<Double?>>
) {
    Text(text = "Orientation: " +
            when (direction.value) {
                is Result.Success<Double?> -> (direction.value as Result.Success<Double?>)
                    .data?.let { String.format("%.2f", it) } ?: "0.00"

                is Result.Error -> "Error"
                is Result.Loading -> "LOADING..."
            }
    )

    Spacer(modifier = Modifier.height(16.dp))

    Text(text = "Distance: " +
            when (distance.value) {
                is Result.Success<Double?> -> (distance.value as Result.Success<Double?>)
                    .data?.let { String.format("%.2f", it) } ?: "0.00"

                is Result.Error -> "Error"
                is Result.Loading -> "LOADING..."
            }
    )
}