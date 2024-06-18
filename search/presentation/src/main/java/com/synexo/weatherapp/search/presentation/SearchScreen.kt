@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.synexo.weatherapp.search.presentation

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.shouldShowRationale
import com.synexo.weatherapp.core.ui.LocalNavAnimatedVisibilityScope
import com.synexo.weatherapp.core.ui.LocalSharedTransitionScope
import com.synexo.weatherapp.core.ui.LocationPermissionTextProvider
import com.synexo.weatherapp.core.ui.components.PermissionDialog
import com.synexo.weatherapp.core.ui.safeNavigate
import com.synexo.weatherapp.core.ui.util.NavRoutes
import com.synexo.weatherapp.core.ui.viewModel.CityWeatherSharedViewModel
import com.synexo.weatherapp.core.ui.viewModel.rememberViewEvent
import com.synexo.weatherapp.search.presentation.components.AddCurrentLocation
import com.synexo.weatherapp.search.presentation.components.SearchInput
import com.synexo.weatherapp.search.presentation.components.SearchList
import com.synexo.weatherapp.search.presentation.util.rememberPermissionStateSafe
import kotlinx.coroutines.delay

const val ANIMATION_DELAY = 250

@Composable
fun SearchScreen(
    navController: NavController,
    sharedStateViewModel: CityWeatherSharedViewModel,
    viewModel: SearchScreenViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()
    val navigationEvent by viewModel.viewModelEvent.collectAsState()

    navigationEvent
        ?.getContentIfNotHandled()
        ?.let { event ->
            when (event) {
                is SearchScreenViewModelEvent.NavigateToAddCityWeather -> {
                    sharedStateViewModel.updateState(event.cityWeather)
                    navController.safeNavigate(NavRoutes.AddCityWeatherScreen.route)
                }

                is SearchScreenViewModelEvent.NavigateToWeatherScreen -> {
                    navController.safeNavigate(NavRoutes.WeatherScreen.createRoute(event.cityIndex))
                }

                is SearchScreenViewModelEvent.NavigateUp -> {
                    navController.navigateUp()
                }

                is SearchScreenViewModelEvent.NavigateUpWithCityUpdateData -> {
                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        "resultKey",
                        event.cityName
                    )
                    navController.navigateUp()
                }
            }
        }

    SearchScreenContent(
        state = state,
        event = rememberViewEvent(viewModel)
    )
}

@ExperimentalSharedTransitionApi
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SearchScreenContent(
    state: SearchScreenState,
    event: (SearchScreenViewEvent) -> Unit
) {
    val context = LocalContext.current
    val focusRequester = remember { FocusRequester() }
    val sharedTransitionScope = LocalSharedTransitionScope.current
        ?: throw IllegalStateException("No shared scope found")
    val animatedVisibilityScope = LocalNavAnimatedVisibilityScope.current
        ?: throw IllegalStateException("No animated scope found")

    val locationPermissionState =
        rememberPermissionStateSafe(Manifest.permission.ACCESS_FINE_LOCATION) { isGranted ->
            if (!isGranted) {
                event(SearchScreenViewEvent.SetLocationPermissionRationaleDialogState(true))
            }
        }

    LaunchedEffect(Unit) {
        delay(500) // Delay to ensure the view is properly loaded
        focusRequester.requestFocus()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        if (state.showLocationPermissionRationaleDialog) {
            PermissionDialog(
                permissionTextProvider = LocationPermissionTextProvider(),
                isPermanentlyDeclined = locationPermissionState.status.shouldShowRationale.not(),
                onDismiss = {
                    event(SearchScreenViewEvent.SetLocationPermissionRationaleDialogState(false))
                },
                onOkClick = {
                    event(SearchScreenViewEvent.SetLocationPermissionRationaleDialogState(false))
                    locationPermissionState.launchPermissionRequest()
                },
                onGoToAppSettingsClick = { context.openAppSettings() }
            )
        }

        SearchInput(
            sharedTransitionScope = sharedTransitionScope,
            animatedVisibilityScope = animatedVisibilityScope,
            focusRequester = focusRequester,
            state = state,
            event = event
        )

        AnimatedVisibility(
            modifier = Modifier,
            visible = state.locations.isEmpty(),
            enter = fadeIn(animationSpec = tween(delayMillis = ANIMATION_DELAY)),
            exit = fadeOut(animationSpec = tween(durationMillis = ANIMATION_DELAY))
        ) {
            AddCurrentLocation(
                permissionState = locationPermissionState,
                isLoading = state.isLocateMeInProgress,
                onEvent = event
            )
        }

        AnimatedVisibility(
            modifier = Modifier,
            enter = fadeIn(animationSpec = tween(delayMillis = ANIMATION_DELAY)),
            exit = fadeOut(animationSpec = tween(durationMillis = ANIMATION_DELAY)),
            visible = state.locations.isNotEmpty()
        ) {
            SearchList(
                locations = state.locations,
                savedCities = state.savedCities.map { it.id },
                searchInput = state.searchInput,
                isLoading = state.isLoading,
                onEvent = event
            )
        }
    }

}

private fun Context.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}
