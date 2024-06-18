package com.synexo.weatherapp.futureDailyForecast.presentation.components


import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.synexo.weatherapp.core.model.weather.Time
import com.synexo.weatherapp.core.ui.LocalCustomColorsPalette
import com.synexo.weatherapp.core.ui.WeatherAppTheme
import com.synexo.weatherapp.core.ui.getLocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.min

@Composable
fun SelectableDateWidget(
    isSelected: Boolean,
    time: Time,
    index: Int,
    onClick: (Int) -> Unit,
) {
    val (dayOfWeek, dayOfMonth) = timeToDayOfWeekAndDayOfMonth(time)

    val (background, text) = with(LocalCustomColorsPalette.current) {
        if (isSelected)
            buttonBackground to textReverse
        else
            background to text
    }

    val backgroundColor by animateColorAsState(background)
    val textColor by animateColorAsState(text)
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onClick(index) }
            .padding(8.dp)
            .wrapContentWidth()
            .background(shape = RoundedCornerShape(8.dp), color = backgroundColor)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = dayOfWeek,
            color = textColor,
            fontSize = 10.sp,
            lineHeight = 10.sp
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = dayOfMonth,
            color = textColor,
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp,
            lineHeight = 10.sp
        )
    }
}


private fun timeToDayOfWeekAndDayOfMonth(
    time: Time,
): Pair<String, String> {
    return time.getLocalDate()
        .format(DateTimeFormatter.ofPattern("E d"))
        .run {
            if (contains(".")) {
                replace(" ", "").split(".")
            } else {
                split(" ")
            }
        }
        .let { (dayOfWeek, dayOfMonth) ->
            val formattedDayOfWeek = dayOfWeek
                .substring(0, min(3, dayOfWeek.length))
                .replaceFirstChar { it.uppercase() }
            formattedDayOfWeek to dayOfMonth
        }
}

@PreviewLightDark
@Composable
fun SelectableDateWidgetPreview(
    @PreviewParameter(StateParameterProvider::class) isSelected: Boolean
) {
    WeatherAppTheme {
        Surface(color = LocalCustomColorsPalette.current.background) {
            SelectableDateWidget(
                isSelected = isSelected,
                time = Time(1630000000, "Europe/London"),
                index = 0,
                onClick = {}
            )
        }
    }
}

private class StateParameterProvider : PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean>
        get() = sequenceOf(true, false)
}