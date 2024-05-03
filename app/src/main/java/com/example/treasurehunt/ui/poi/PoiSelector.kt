package com.example.treasurehunt.ui.poi

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
    selectPoi: (poiItem: Result<PoiItem?>) -> Unit
) {

    Button(
        onClick = {
            selectPoi(closestPoi.value)
        },
        enabled = isButtonEnabled.value
    ) {
        Text("Select POI")
    }
}