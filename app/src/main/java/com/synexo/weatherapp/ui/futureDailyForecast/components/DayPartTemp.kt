package com.synexo.weatherapp.ui.futureDailyForecast.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.synexo.weatherapp.R
import com.synexo.weatherapp.ui.theme.LocalCustomColorsPalette
import com.synexo.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun DayPartTemp(
    timeOfDay: Int,
    temperature: String,
    feelsLikeTemperature: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.padding(bottom = 10.dp),
            text = stringResource(id = timeOfDay),
            fontSize = 12.sp,
            lineHeight = 12.sp,
            fontWeight = FontWeight.Medium,
            color = LocalCustomColorsPalette.current.textSecondary
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            ImageWithText(
                temperature = temperature,
                image = R.drawable.ic_temp
            )

            Spacer(modifier = Modifier.width(16.dp))

            ImageWithText(
                temperature = feelsLikeTemperature,
                image = R.drawable.ic_feels_like_temp
            )
        }
    }
}

@Composable
private fun ImageWithText(
    temperature: String,
    @DrawableRes image: Int
) {
    Image(
        modifier = Modifier.size(16.dp),
        painter = painterResource(id = image),
        contentDescription = null
    )

    Spacer(modifier = Modifier.width(2.dp))

    Text(
        text = temperature,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
    )
}

@PreviewLightDark
@Composable
fun DayPartWeatherItemPreview() {
    WeatherAppTheme {
        Surface(color = LocalCustomColorsPalette.current.background) {
            DayPartTemp(
                timeOfDay = R.string.common_morning,
                temperature = "12°C",
                feelsLikeTemperature = "10°C"
            )
        }
    }
}