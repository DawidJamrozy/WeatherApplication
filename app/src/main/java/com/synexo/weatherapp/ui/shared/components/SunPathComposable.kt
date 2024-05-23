package com.synexo.weatherapp.ui.shared.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.synexo.weatherapp.ui.theme.SunflowerMango
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun SunPathComposable(
    min: Float,
    max: Float,
    current: Float,
    modifier: Modifier = Modifier
) {
    val pathStrokeWidth = 1.dp // The stroke width of the dashed path
    Canvas(modifier = modifier.size(120.dp, 60.dp)) {
        val dotRadius = 3.dp.toPx()
        val startDot = Offset(x = 0f, y = size.height - dotRadius)
        val endDot = Offset(x = size.width, y = size.height - dotRadius)
        val center = Offset(x = size.width / 2, y = 0f)

        // Draw fixed dots
        drawCircle(color = Color.Gray, radius = dotRadius, center = startDot)
        drawCircle(color = Color.Gray, radius = dotRadius, center = endDot)

        // Draw semi-circle
        val path = Path().apply {
            moveTo(0f, size.height)
            arcTo(
                rect = Rect(
                    left = 0f,
                    top = 0f,
                    right = size.width,
                    bottom = size.height * 2
                ),
                startAngleDegrees = 180f,
                sweepAngleDegrees = 180f,
                forceMoveTo = false
            )
        }

        // Draw dashed path
        drawPath(
            path = path,
            color = Color.Gray,
            style = Stroke(
                width = pathStrokeWidth.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            )
        )

        // Draw gradient path - background
        drawPath(
            path = path,
            brush = Brush.horizontalGradient(
                colors = listOf(Color.Gray.copy(alpha = 0.1f), Color.Transparent)
            ),
        )

        if (current > min && current < max) {
            // Calculate and draw moving dot position
            val sunSize = 7.dp // The size of the sun circle
            val normalizedCurrent = (current - min) / (max - min)
            val angle = PI * (1 - normalizedCurrent)
            val movingDotX = center.x + (size.width / 2 * cos(angle)).toFloat()
            val movingDotY = center.y + (size.width / 2 * sin(angle)).toFloat()
            val movingDot = Offset(movingDotX, size.height - movingDotY)
            drawCircle(
                color = SunflowerMango,
                radius = sunSize.toPx() / 2,
                center = movingDot
            )

            // Draw sun rays
            val numberOfRays = 8 // Number of rays you want to draw
            val rayLength = 7.dp.toPx() // Length of each ray
            val rayStrokeWidth = 1.dp.toPx() // Stroke width of each ray
            for (i in 0 until numberOfRays) {
                val rayAngle = (2 * PI / numberOfRays) * i
                val rayEndX = movingDot.x + rayLength * cos(rayAngle).toFloat()
                val rayEndY = movingDot.y + rayLength * sin(rayAngle).toFloat()
                drawLine(
                    color = SunflowerMango,
                    start = movingDot,
                    end = Offset(rayEndX, rayEndY),
                    strokeWidth = rayStrokeWidth
                )
            }
        }
    }
}

@Preview
@Composable
fun SunPathComposablePreview() {
    SunPathComposable(min = 0f, max = 100f, current = 30f)
}
