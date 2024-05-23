package com.synexo.weatherapp.ui.settings.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.synexo.weatherapp.R
import com.synexo.weatherapp.data.model.ThemeType
import com.synexo.weatherapp.ui.theme.LocalCustomColorsPalette
import com.synexo.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun ThemeRowItem(
    isSelected: Boolean,
    theme: ThemeType,
    name: Int,
    icon: Int,
    iconColor: Color? = null,
    onClick: (ThemeType) -> Unit
) {
    Row(
        modifier = Modifier
            .clickable { onClick(theme) }
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .alpha(if (isSelected) 1f else 0f)
                .padding(end = 8.dp),
            imageVector = Icons.Filled.Check,
            tint = LocalCustomColorsPalette.current.iconClickable,
            contentDescription = null
        )
        Text(
            text = stringResource(id = name),
            color = LocalCustomColorsPalette.current.text
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            colorFilter = iconColor?.let { ColorFilter.tint(it) },
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
        )
    }
}

@PreviewLightDark
@Composable
fun PreviewThemeRowItem() {
    WeatherAppTheme {
        Surface(color = LocalCustomColorsPalette.current.background) {
            ThemeRowItem(
                isSelected = true,
                theme = ThemeType.Light,
                name = R.string.screen_settings_theme_light,
                icon = R.drawable.ic_sun,
                onClick = {}
            )
        }
    }
}