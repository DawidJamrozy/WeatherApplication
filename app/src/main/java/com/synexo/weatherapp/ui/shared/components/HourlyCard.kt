package com.synexo.weatherapp.ui.shared.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
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
import com.synexo.weatherapp.domain.model.weather.HourlyWeather
import com.synexo.weatherapp.ui.theme.LocalCustomColorsPalette
import com.synexo.weatherapp.ui.theme.WeatherAppTheme
import com.synexo.weatherapp.util.MockDataHelper
import com.synexo.weatherapp.util.extensions.HOUR_MINUTE_PATTERN
import com.synexo.weatherapp.util.extensions.getDateIfMidnight
import com.synexo.weatherapp.util.extensions.getIconResId
import com.synexo.weatherapp.util.extensions.isNow
import com.synexo.weatherapp.util.extensions.toDate

@Composable
fun HourlyCard(
    hourlyWeather: HourlyWeather,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        hourlyWeather
            .getTimestamp()
            .getDateIfMidnight()
            ?.let {
                Text(
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .align(Alignment.CenterHorizontally),
                    text = it,
                    fontSize = 10.sp,
                    lineHeight = 10.sp,
                    color = LocalCustomColorsPalette.current.textSecondary
                )
            }
            ?: Spacer(Modifier.size(20.dp))

        Card(
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(LocalCustomColorsPalette.current.cardBackgroundGradient),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.size(28.dp),
                    painter = painterResource(id = hourlyWeather.details.getIconResId()),
                    contentDescription = null
                )
            }
        }

        Spacer(Modifier.height(4.dp))

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = if (hourlyWeather.getTimestamp().isNow()) {
                stringResource(R.string.common_now)
            } else {
                hourlyWeather.getTimestamp().toDate(HOUR_MINUTE_PATTERN)
            },
            fontSize = 10.sp,
            lineHeight = 10.sp,
            color = LocalCustomColorsPalette.current.textSecondary
        )

        Spacer(Modifier.height(4.dp))

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = hourlyWeather.temp.getValueWithUnit(),
            fontSize = 12.sp,
            lineHeight = 12.sp,
            color = LocalCustomColorsPalette.current.text,
            fontWeight = FontWeight.Medium
        )
    }
}

@PreviewLightDark
@Composable
fun HourlyCardPreview() {
    WeatherAppTheme {
        Surface(color = LocalCustomColorsPalette.current.background) {
            HourlyCard(
                hourlyWeather = MockDataHelper.createHourlyWeather()
            )
        }
    }
}