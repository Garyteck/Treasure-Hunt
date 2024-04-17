package com.example.treasurehunt.ui.congratulations

import androidx.compose.animation.AnimatedContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun CongratulationsScreen(
    isAllFound: Boolean,
) {
    if (!isAllFound) return

    Congratulations(isAllFound)
}

@Composable
fun Congratulations(allPoiSelected: Boolean) {
    AnimatedContent(allPoiSelected, label = "congratulations") {
        if (it) Text("Congratulations! You found all the POIs!")
    }
}
