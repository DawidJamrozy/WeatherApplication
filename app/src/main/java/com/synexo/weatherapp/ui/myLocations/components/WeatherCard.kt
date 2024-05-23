package com.synexo.weatherapp.ui.myLocations.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.synexo.weatherapp.R
import com.synexo.weatherapp.domain.model.weather.CityDetails
import com.synexo.weatherapp.ui.theme.LocalCustomColorsPalette
import com.synexo.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun WeatherCard(
    modifier: Modifier,
    cityDetails: CityDetails,
    time: String,
    description: String,
    temperature: String,
    highLowTemperature: String,
    icon: Int,
    elevation: Dp,
    onClick:() -> Unit,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        shadowElevation = elevation,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(LocalCustomColorsPalette.current.cardBackgroundGradient)
                .clickable { onClick() }
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(
                    modifier = Modifier.padding(bottom = 2.dp),
                    text = time,
                    color = LocalCustomColorsPalette.current.textSecondary,
                    fontSize = 10.sp,
                    lineHeight = 10.sp
                )
                Text(
                    text = cityDetails.name,
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                    color = LocalCustomColorsPalette.current.text,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    modifier = Modifier.padding(bottom = 4.dp),
                    text = "${cityDetails.administrativeArea}, ${cityDetails.country}",
                    fontSize = 9.sp,
                    lineHeight = 9.sp,
                    color = LocalCustomColorsPalette.current.textSecondary
                )
                Text(
                    text = description,
                    fontSize = 10.sp,
                    lineHeight = 10.sp,
                    color = LocalCustomColorsPalette.current.text
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = temperature,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = LocalCustomColorsPalette.current.text
                )
                Text(
                    text = highLowTemperature,
                    color = LocalCustomColorsPalette.current.text,
                    fontSize = 10.sp,
                    lineHeight = 10.sp,
                )
            }

            Image(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .size(50.dp),
                painter = painterResource(id = icon),
                contentDescription = null,
            )
        }
    }
}

@PreviewLightDark
@Composable
fun PreviewWeatherCard() {
    WeatherAppTheme {
        Surface(color = LocalCustomColorsPalette.current.background) {
            WeatherCard(
                cityDetails = CityDetails(
                    name = "Rzeszów",
                    administrativeArea = "Podkarpackie",
                    country = "Poland",
                    language = "pl"
                ),
                time = "19:19",
                description = "Partially cloudy",
                temperature = "13°C",
                highLowTemperature = "↑ 18°C ↓ 9°C",
                icon = R.drawable.ic_sun,
                modifier = Modifier.fillMaxWidth(),
                elevation = 1.dp,
                onClick = {}
            )
        }
    }
}