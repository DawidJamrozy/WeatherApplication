package com.synexo.weatherapp.core.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.synexo.weatherapp.core.ui.LocalCustomColorsPalette
import com.synexo.weatherapp.core.ui.WeatherAppTheme
import com.synexo.weatherapp.core.ui.R as CoreR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForecastTopBar(
    @StringRes title: Int,
    @DrawableRes iconRes: Int,
    onBackClick:() -> Unit,
    onIconClick:() -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = LocalCustomColorsPalette.current.background
        ),
        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = title),
                    style = MaterialTheme.typography.titleMedium,
                    color = LocalCustomColorsPalette.current.text
                )
            }
        },
        navigationIcon = {
            IconButton(
                onClick = onBackClick,
            ) {
                Icon(
                    tint = LocalCustomColorsPalette.current.iconClickable,
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = stringResource(id = CoreR.string.common_cancel),
                    modifier = Modifier.size(20.dp)
                )
            }
        },
        actions = {
            IconButton(
                onClick = onIconClick,
            ) {
                Icon(
                    tint = LocalCustomColorsPalette.current.iconClickable,
                    painter = painterResource(id = iconRes),
                    contentDescription = stringResource(id = CoreR.string.common_rain),
                )
            }
        }
    )
}

@PreviewLightDark
@Composable
fun ForecastTopBarPreview() {
    WeatherAppTheme {
        Surface(color = LocalCustomColorsPalette.current.background) {
            ForecastTopBar(
                title = CoreR.string.permission_dialog_title,
                onBackClick = {},
                onIconClick = {},
                iconRes = CoreR.drawable.ic_temp
            )
        }
    }
}