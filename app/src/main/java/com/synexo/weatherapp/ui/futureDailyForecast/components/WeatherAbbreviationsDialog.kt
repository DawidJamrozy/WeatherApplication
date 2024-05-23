package com.synexo.weatherapp.ui.futureDailyForecast.components

import android.view.Gravity
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogWindowProvider
import com.synexo.weatherapp.R
import com.synexo.weatherapp.ui.theme.LocalCustomColorsPalette
import com.synexo.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun WeatherAbbreviationsDialog(onDialogClose: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    Dialog(
        onDismissRequest = onDialogClose
    ) {
        val dialogWindowProvider = LocalView.current.parent as DialogWindowProvider
        dialogWindowProvider.window.setGravity(Gravity.END or Gravity.TOP)

        Card(
            modifier = Modifier
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) { onDialogClose() }
                .padding(35.dp)
                .background(Color.Transparent),
            content = {
                Column(
                    modifier = Modifier
                        .background(LocalCustomColorsPalette.current.background)
                        .padding(20.dp)
                ) {
                    Text(
                        text = stringResource(R.string.common_abbreviations),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )

                    getAbbreviationRowData()
                        .forEach { (icon, text) ->
                            AbbreviationRow(
                                icon = icon,
                                text = text
                            )
                        }
                }
            }
        )

    }
}

@Composable
private fun AbbreviationRow(
    @DrawableRes icon: Int,
    @StringRes text: Int
) {
    Row(
        modifier = Modifier.padding(top = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(16.dp),
            painter = painterResource(id = icon),
            contentDescription = null,
        )

        Text(
            text = stringResource(id = text),
            modifier = Modifier.padding(start = 8.dp),
            fontSize = 12.sp,
            color = LocalCustomColorsPalette.current.text,
        )

    }
}

private fun getAbbreviationRowData(): List<Pair<Int, Int>> {
    return listOf(
        R.drawable.ic_temp to R.string.common_temperature,
        R.drawable.ic_feels_like_temp to R.string.common_feels_like_temp,
        R.drawable.ic_sunrise to R.string.common_sunrise,
        R.drawable.ic_sunset to R.string.common_sunset,
        R.drawable.ic_sun to R.string.common_daylight,
        R.drawable.moon to R.string.common_moonrise,
        R.drawable.ic_pressure to R.string.common_pressure,
        R.drawable.ic_humidity to R.string.common_humidity,
        R.drawable.ic_wind_speed to R.string.common_wind_speed_title,
        R.drawable.ic_clouds to R.string.common_cloudiness,
        R.drawable.ic_percent to R.string.common_pop,
        R.drawable.ic_uv_index to R.string.common_uv,
        R.drawable.ic_wind_gust to R.string.common_wind_gust,
        R.drawable.ic_snow to R.string.common_snow,
        R.drawable.ic_clouds_rain to R.string.common_rain
    )
}

@PreviewLightDark
@Composable
fun AbbreviationRowPreview() {
    WeatherAppTheme {
        Surface(color = LocalCustomColorsPalette.current.background) {
            AbbreviationRow(
                icon = R.drawable.ic_wind_gust,
                text = R.string.common_wind_gust
            )
        }
    }
}

@PreviewLightDark
@Composable
fun WeatherAbbreviationsDialogPreview() {
    WeatherAppTheme {
        Surface(color = LocalCustomColorsPalette.current.background) {
            WeatherAbbreviationsDialog() {}
        }
    }
}