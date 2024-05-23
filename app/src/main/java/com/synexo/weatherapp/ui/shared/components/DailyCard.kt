package com.synexo.weatherapp.ui.shared.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.synexo.weatherapp.domain.model.weather.DailyWeather
import com.synexo.weatherapp.ui.theme.LocalCustomColorsPalette
import com.synexo.weatherapp.ui.theme.WeatherAppTheme
import com.synexo.weatherapp.util.MockDataHelper
import com.synexo.weatherapp.util.extensions.SHORT_DAY_DATE_MONTH_PATTERN
import com.synexo.weatherapp.util.extensions.getIconResId
import com.synexo.weatherapp.util.extensions.toReadableDate

@Composable
fun DailyCard(
    dailyWeather: DailyWeather,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        Card(
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.CenterVertically)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(LocalCustomColorsPalette.current.cardBackgroundGradient),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.size(28.dp),
                    painter = painterResource(id = dailyWeather.details.getIconResId()),
                    contentDescription = null
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = dailyWeather.getTimestamp().toReadableDate(SHORT_DAY_DATE_MONTH_PATTERN),
                fontSize = 11.sp,
                lineHeight = 11.sp,
                color = LocalCustomColorsPalette.current.text
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = dailyWeather.details.description.replaceFirstChar { it.uppercase() },
                lineHeight = 10.sp,
                fontSize = 10.sp,
                color = LocalCustomColorsPalette.current.textSecondary
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier
                .width(125.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = dailyWeather.temp.min.getValueWithUnit(),
                    color = LocalCustomColorsPalette.current.textSecondary,
                    fontWeight = FontWeight.Medium,
                    fontSize = 10.sp
                )
                Text(
                    text = dailyWeather.temp.max.getValueWithUnit(),
                    color = LocalCustomColorsPalette.current.text,
                    fontWeight = FontWeight.Medium,
                    fontSize = 10.sp
                )
            }

            DrawLine(
                nonHighlightedColor = LocalCustomColorsPalette.current.textSecondary.copy(alpha = 0.5f)
            )
        }
    }
}

@Composable
fun DrawLine(
    nonHighlightedColor: Color,
) {
    Canvas(
        modifier = Modifier
            .height(4.dp)
            .fillMaxWidth()
    ) {
        val strokeWidth = 4.dp.toPx()
        val totalLength = size.width

        // Draw first part of the line
        drawLine(
            color = nonHighlightedColor,
            start = Offset(0f, 0f),
            end = Offset(totalLength, 0f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
    }
}

@PreviewLightDark
@Composable
fun DailyCardPreview() {
    WeatherAppTheme {
        Surface(color = LocalCustomColorsPalette.current.background) {
            DailyCard(
                dailyWeather = MockDataHelper.createDailyWeather(),
            )
        }
    }
}