package com.synexo.weatherapp.futureDailyForecast.presentation.components

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
import com.synexo.weatherapp.core.ui.LocalCustomColorsPalette
import com.synexo.weatherapp.core.ui.WeatherAppTheme
import com.synexo.weatherapp.core.ui.R as CoreR

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
                        text = stringResource(CoreR.string.common_abbreviations),
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
        CoreR.drawable.ic_temp to CoreR.string.common_temperature,
        CoreR.drawable.ic_feels_like_temp to CoreR.string.common_feels_like_temp,
        CoreR.drawable.ic_sunrise to CoreR.string.common_sunrise,
        CoreR.drawable.ic_sunset to CoreR.string.common_sunset,
        CoreR.drawable.ic_sun to CoreR.string.common_daylight,
        CoreR.drawable.moon to CoreR.string.common_moonrise,
        CoreR.drawable.ic_pressure to CoreR.string.common_pressure,
        CoreR.drawable.ic_humidity to CoreR.string.common_humidity,
        CoreR.drawable.ic_wind_speed to CoreR.string.common_wind_speed_title,
        CoreR.drawable.ic_clouds to CoreR.string.common_cloudiness,
        CoreR.drawable.ic_percent to CoreR.string.common_pop,
        CoreR.drawable.ic_uv_index to CoreR.string.common_uv,
        CoreR.drawable.ic_wind_gust to CoreR.string.common_wind_gust,
        CoreR.drawable.ic_snow to CoreR.string.common_snow,
        CoreR.drawable.ic_clouds_rain to CoreR.string.common_rain
    )
}

@PreviewLightDark
@Composable
fun AbbreviationRowPreview() {
    WeatherAppTheme {
        Surface(color = LocalCustomColorsPalette.current.background) {
            AbbreviationRow(
                icon = CoreR.drawable.ic_wind_gust,
                text = CoreR.string.common_wind_gust
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