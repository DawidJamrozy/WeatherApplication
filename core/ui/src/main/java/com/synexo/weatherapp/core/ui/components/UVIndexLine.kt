package com.synexo.weatherapp.core.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.synexo.weatherapp.core.ui.Apple
import com.synexo.weatherapp.core.ui.BlazeOrange
import com.synexo.weatherapp.core.ui.RedOrange
import com.synexo.weatherapp.core.ui.Rose
import com.synexo.weatherapp.core.ui.SelectiveYellow


@Composable
fun UVIndexLine(indexOfText: Int) {
    Row(
        modifier = Modifier
            .wrapContentWidth(),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val uvIndex = indexOfText.coerceIn(0, 11)
        for (i in 0 until 12) {
            if (i == uvIndex) {
                Text(
                    text = "$uvIndex",
                    fontSize = 15.sp,
                    color = getColors(i),
                    fontWeight = FontWeight.SemiBold
                )
            } else {
                Canvas(
                    modifier = Modifier
                        .width(4.dp)
                ) {
                    drawLine(
                        color = getColors(i),
                        strokeWidth = 2.dp.toPx(),
                        start = Offset(0f, size.height / 2),
                        end = Offset(size.width, size.height / 2)
                    )
                }
            }
        }
    }
}

private fun getColors(index: Int): Color {
    return when (index) {
        0, 1 -> Apple
        2, 3, 4 -> SelectiveYellow
        5, 6 -> BlazeOrange
        7, 8, 9 -> RedOrange
        10, 11 -> Rose
        else -> Color.Black
    }
}

@Preview
@Composable
fun DottedRectanglesWithTextPreview(
    @PreviewParameter(UVIndexParameterProvider::class) uvIndex: Int
) {
    UVIndexLine(indexOfText = uvIndex)
}

class UVIndexParameterProvider : PreviewParameterProvider<Int> {
    override val values = sequenceOf(
        -1,0,1,2,3,4,5,6,7,8,10,11
    )
}
