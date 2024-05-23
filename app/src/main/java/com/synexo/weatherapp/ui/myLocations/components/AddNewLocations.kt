package com.synexo.weatherapp.ui.myLocations.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.synexo.weatherapp.R
import com.synexo.weatherapp.ui.theme.LocalCustomColorsPalette
import com.synexo.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun AddNewLocations() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_location),
            contentDescription = null,
            colorFilter = ColorFilter.tint(LocalCustomColorsPalette.current.icon)
        )

        Spacer(Modifier.padding(5.dp))

        Text(
            text = stringResource(R.string.screen_my_locations_add_new_locations),
            color = LocalCustomColorsPalette.current.text,
            fontSize = 16.sp
        )

        Text(
            text = stringResource(R.string.screen_my_locations_use_search_bar),
            textAlign = TextAlign.Center,
            color = LocalCustomColorsPalette.current.inputTextLabelIcon,
            fontSize = 12.sp
        )
    }
}

@PreviewLightDark
@Composable
fun AddNewLocationsPreview() {
    WeatherAppTheme {
        Surface(color = LocalCustomColorsPalette.current.background) {
            AddNewLocations()
        }
    }
}