package com.example.treasurehunt.ui.compass

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import com.example.treasurehunt.Result

@Composable
fun Compass(
    arrowDirection: State<Result<Double?>>,
    text: State<Result<Double?>>,
    modifier: Modifier = Modifier,
    textMeasurer: TextMeasurer = rememberTextMeasurer(),
) {

    val angle = when (arrowDirection.value) {
        is Result.Error -> 0.0
        is Result.Loading -> 0.0
        is Result.Success -> (arrowDirection.value as Result.Success<Double?>).data!!
    }

    when (text.value) {
        is Result.Error -> return
        is Result.Loading -> return
        is Result.Success -> {}
    }

    val circleRadius = 150.dp // Adjust radius as needed

    val textLayoutResult: TextLayoutResult =
        textMeasurer.measure(text = AnnotatedString(text.value.toString()))
    val textSize = textLayoutResult.size

    Box(modifier = modifier.size(circleRadius * 2)) {
        Column {
            Text(text = "angle : $angle")

        }
        Canvas(modifier = Modifier.fillMaxSize()) {


            // Draw the circle
            drawArc(
                color = Color.Black,
                startAngle = (angle.toFloat() - 90F) - 45F,
                sweepAngle = 90F,
                useCenter = false,
                topLeft = Offset(
                    center.x - circleRadius.toPx(),
                    center.y - circleRadius.toPx()
                ),
                style = Stroke(24F),
            )


            // Draw the arrow path
//            val arrowPath = Path().apply {
//                val arrowAngle = (angle - 90f) % 360f // Adjust for 0 degrees at north
//                val arrowRadians = Math.toRadians(arrowAngle.toDouble())
//                val arrowTipX = center.x + 150.dp.toPx() * cos(arrowRadians).toFloat()
//                val arrowTipY = center.y + 150.dp.toPx() * sin(arrowRadians).toFloat()
//
//                moveTo(center.x, center.y)
//                lineTo(arrowTipX - 45.dp.toPx(), arrowTipY + 45.dp.toPx())
//                lineTo(arrowTipX + 45.dp.toPx(), arrowTipY - 45.dp.toPx())
//                close()
//            }
//            drawPath(path = arrowPath, color = Color.Black)

            // Draw the text inside the circle
            val canvasWidth = size.width
            val canvasHeight = size.height

            drawText(
                textMeasurer = textMeasurer,
                text = (text.value as Result.Success<Double?>).data.toString(),
                topLeft = Offset(
                    (canvasWidth - textSize.width) / 2f,
                    (canvasHeight - textSize.height) / 2f
                ),
            )


        }
    }
}


