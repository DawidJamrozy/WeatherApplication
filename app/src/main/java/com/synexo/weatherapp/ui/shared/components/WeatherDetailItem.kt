package com.synexo.weatherapp.ui.shared.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.synexo.weatherapp.R
import com.synexo.weatherapp.ui.theme.LocalCustomColorsPalette
import com.synexo.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun WeatherDetailItem(
    @DrawableRes icon: Int,
    title: String, text: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Image(
            modifier = Modifier.size(16.dp),
            painter = painterResource(id = icon),
            contentDescription = null,
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = title,
                color = LocalCustomColorsPalette.current.textSecondary,
                fontSize = 9.sp,
                lineHeight = 9.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = text,
                color = LocalCustomColorsPalette.current.text,
                fontSize = 12.sp,
                lineHeight = 12.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@PreviewLightDark
@Composable
fun WeatherDetailItemPreview() {
    WeatherAppTheme {
        Surface(color = LocalCustomColorsPalette.current.background) {
            WeatherDetailItem(
                icon = R.drawable.ic_humidity,
                title = "Humidity",
                text = "48%"
            )
        }
    }
}