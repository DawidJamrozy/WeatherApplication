package com.synexo.weatherapp.search.presentation.components

import android.Manifest
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.synexo.weatherapp.core.ui.LocalCustomColorsPalette
import com.synexo.weatherapp.core.ui.WeatherAppTheme
import com.synexo.weatherapp.search.presentation.R
import com.synexo.weatherapp.search.presentation.SearchScreenViewEvent
import com.synexo.weatherapp.search.presentation.util.rememberPermissionStateSafe

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AddCurrentLocation(
    permissionState: PermissionState,
    isLoading: Boolean,
    onEvent: (SearchScreenViewEvent) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val animatedWidth by animateFloatAsState(targetValue = if (isLoading) 0.4f else 0.8f)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_locate_me),
            contentDescription = null,
            colorFilter = ColorFilter.tint(LocalCustomColorsPalette.current.icon)
        )

        Spacer(Modifier.padding(5.dp))

        Text(
            text = stringResource(R.string.screen_search_add_current_location),
            color = LocalCustomColorsPalette.current.text,
            fontSize = 16.sp
        )

        Text(
            text = stringResource(R.string.screen_search_add_your_location_details),
            textAlign = TextAlign.Center,
            color = LocalCustomColorsPalette.current.inputTextLabelIcon,
            fontSize = 12.sp
        )

        Button(
            onClick = {
                if (permissionState.status.isGranted) {
                    onEvent(SearchScreenViewEvent.OnLocationPermissionGranted)
                    keyboardController?.hide()
                } else {
                    permissionState.launchPermissionRequest()
                }
            },
            modifier = Modifier
                .fillMaxWidth(animatedWidth)
                .padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = LocalCustomColorsPalette.current.textClickable
            ),
            shape = CircleShape
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(24.dp),
                    strokeWidth = 3.dp,
                    color = LocalCustomColorsPalette.current.textReverse,
                )
            } else {
                Text(
                    text = stringResource(R.string.screen_search_find_me),
                    color = LocalCustomColorsPalette.current.textReverse,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@PreviewLightDark
@Composable
fun AddCurrentLocationPreview(
    @PreviewParameter(AddCurrentLocationParameterProvider::class) isLoading: Boolean
) {
    WeatherAppTheme {
        Surface(color = LocalCustomColorsPalette.current.background) {
            AddCurrentLocation(
                permissionState = rememberPermissionStateSafe(Manifest.permission.ACCESS_FINE_LOCATION) {},
                isLoading = isLoading,
                onEvent = { }
            )
        }
    }
}


private class AddCurrentLocationParameterProvider : PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean>
        get() = sequenceOf(true, false)
}
