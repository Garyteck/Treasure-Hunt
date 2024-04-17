package com.example.treasurehunt.ui.poiselector

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import com.example.treasurehunt.Result
import com.example.treasurehunt.data.model.PoiItem

@Composable
fun PoiSelector(
    isButtonEnabled: State<Boolean>,
    closestPoi: State<Result<PoiItem?>>,
    selectPoi: (poiItem: PoiItem) -> Unit
) {

    Button(
        onClick = {
            when (closestPoi.value) {
                is Result.Success<PoiItem?> -> {
                    (closestPoi.value as Result.Success<PoiItem?>).data?.let { selectPoi(it) }
                }
                is Result.Error -> TODO ("Fix this issue")
                is Result.Loading -> TODO(" Fix this issue")
            }
        },
        enabled = isButtonEnabled.value
    ) {
        Text("Select POI")
    }
}