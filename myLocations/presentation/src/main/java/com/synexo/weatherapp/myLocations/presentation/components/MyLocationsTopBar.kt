package com.synexo.weatherapp.myLocations.presentation.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.synexo.weatherapp.core.ui.LocalCustomColorsPalette
import com.synexo.weatherapp.core.ui.WeatherAppTheme
import com.synexo.weatherapp.myLocations.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyLocationsTopBar(
    isEditButtonVisible: Boolean,
    isInEditMode: Boolean,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    CenterAlignedTopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = LocalCustomColorsPalette.current.background
        ),
        title = {
                Text(
                    text = stringResource(R.string.screen_my_locations_title),
                    style = MaterialTheme.typography.titleMedium,
                    color = LocalCustomColorsPalette.current.text
                )
        },
        actions = {
            if(isEditButtonVisible) {
                Text(
                    text = stringResource(if (isInEditMode) R.string.screen_my_locations_done else R.string.screen_my_locations_edit),
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = ripple(bounded = false),
                        ) { onEditClick() }
                        .padding(10.dp),
                    fontSize = 14.sp,
                    color = LocalCustomColorsPalette.current.textClickable
                )
            }
        }
    )
}

@PreviewLightDark
@Composable
fun MyLocationsTopBarPreview(
    @PreviewParameter(MyLocationsTopBarStateParameterProvider::class) state: Boolean
) {
    WeatherAppTheme {
        Surface(color = LocalCustomColorsPalette.current.background) {
            MyLocationsTopBar(
                isEditButtonVisible = true,
                isInEditMode = state,
                onEditClick = {}
            )
        }
    }
}

private class MyLocationsTopBarStateParameterProvider : PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean>
        get() = sequenceOf(true, false)
}