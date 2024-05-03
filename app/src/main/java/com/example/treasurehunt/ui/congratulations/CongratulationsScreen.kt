package com.example.treasurehunt.ui.congratulations

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.treasurehunt.R

@Composable
fun CongratulationsScreen(
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LinearProgressIndicator(
            progress = { 1F },
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Congratulations()

        Spacer(modifier = Modifier.height(16.dp))

        Spacer(modifier = Modifier.height(16.dp))

        RestartButton {
            onClick()
        }
    }
}

@Composable
fun Congratulations() {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.gameover))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        composition = composition,
        progress = { progress }
    )
}

@Composable
fun RestartButton(onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text("Restart Game")
    }
}
