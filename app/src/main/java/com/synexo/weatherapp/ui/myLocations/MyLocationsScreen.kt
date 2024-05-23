package com.synexo.weatherapp.ui.myLocations

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.shouldShowRationale
import com.synexo.weatherapp.R
import com.synexo.weatherapp.data.model.SearchCityDetails
import com.synexo.weatherapp.ui.myLocations.Const.ANIMATION_DELAY
import com.synexo.weatherapp.ui.myLocations.components.AddCurrentLocation
import com.synexo.weatherapp.ui.myLocations.components.AddNewLocations
import com.synexo.weatherapp.ui.myLocations.components.MyLocationsTopBar
import com.synexo.weatherapp.ui.myLocations.components.SavedCities
import com.synexo.weatherapp.ui.myLocations.components.SearchInputRow
import com.synexo.weatherapp.ui.myLocations.components.SearchList
import com.synexo.weatherapp.ui.shared.components.AcceptRejectDialog
import com.synexo.weatherapp.ui.shared.components.ConfirmDialog
import com.synexo.weatherapp.ui.shared.components.ErrorDialog
import com.synexo.weatherapp.ui.shared.components.PermissionDialog
import com.synexo.weatherapp.ui.shared.viewModel.CityWeatherSharedViewModel
import com.synexo.weatherapp.ui.theme.LocalCustomColorsPalette
import com.synexo.weatherapp.ui.theme.WeatherAppTheme
import com.synexo.weatherapp.util.LocationPermissionTextProvider
import com.synexo.weatherapp.util.MockDataHelper
import com.synexo.weatherapp.util.NavRoutes
import com.synexo.weatherapp.util.extensions.rememberPermissionStateSafe
import com.synexo.weatherapp.util.extensions.rememberViewEvent
import com.synexo.weatherapp.util.extensions.safeNavigate

private object Const {
    const val ANIMATION_DELAY = 250
}

@Composable
fun MyLocationsScreen(
    navController: NavController,
    sharedStateViewModel: CityWeatherSharedViewModel,
    viewModel: MyLocationsScreenViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()
    val navigationEvent by viewModel.viewModelEvent.collectAsState()

    navigationEvent
        ?.getContentIfNotHandled()
        ?.let { event ->
            when (event) {
                is MyLocationsScreenViewModelEvent.NavigateToAddCityWeather -> {
                    sharedStateViewModel.updateState(event.cityWeather)
                    navController.safeNavigate(NavRoutes.AddCityWeatherScreen.route)
                }

                is MyLocationsScreenViewModelEvent.NavigateToWeatherScreen -> {
                    navController.safeNavigate(NavRoutes.WeatherScreen.createRoute(event.cityIndex))
                }
            }
        }

    MyLocationsScreenContent(
        state = state,
        event = rememberViewEvent(viewModel)
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MyLocationsScreenContent(
    state: MyLocationsScreenState,
    event: (MyLocationsScreenViewEvent) -> Unit
) {
    val context = LocalContext.current

    val locationPermissionState =
        rememberPermissionStateSafe(android.Manifest.permission.ACCESS_FINE_LOCATION) { isGranted ->
            if (!isGranted) {
                event(MyLocationsScreenViewEvent.SetLocationPermissionRationaleDialogState(true))
            }
        }

    LaunchedEffect(key1 = state.searchInput.isNotEmpty()) {
        if (state.searchInput.isNotEmpty()) {
            event(MyLocationsScreenViewEvent.SetSearchInputFocus(true))
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        val (topBar, editText, focusLocation, focusNoLocation, noFocusNoLocation, searchCityList) = createRefs()

        AnimatedVisibility(
            modifier = Modifier.constrainAs(topBar) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            visible = !state.isSearchInputFocused
        ) {
            MyLocationsTopBar(
                isEditButtonVisible = state.savedCities.isNotEmpty(),
                isInEditMode = state.isInEditMode,
                onEditClick = { event(MyLocationsScreenViewEvent.SwitchEditMode) },
            )
        }

        state.deleteCityDialogData?.let { data ->
            AcceptRejectDialog(
                title = stringResource(id = R.string.screen_my_locations_delete_location_title),
                message = stringResource(
                    id = R.string.screen_my_locations_delete_location_message,
                    data.cityName
                ),
                acceptButtonText = stringResource(id = R.string.common_yes),
                rejectButtonText = stringResource(id = R.string.common_no),
                onAccept = { event(MyLocationsScreenViewEvent.DeleteLocation(data.index)) },
                onReject = { event(MyLocationsScreenViewEvent.HideDeleteLocationDialog) },
                onDismissRequest = { event(MyLocationsScreenViewEvent.HideDeleteLocationDialog) },
            )
        }

        state.cityUpdatedDialogData?.let { data ->
            ConfirmDialog(
                onDialogClose = {
                    event(MyLocationsScreenViewEvent.HideCityUpdatedDialog)
                },
                title = stringResource(R.string.screen_my_locations_city_data_updated_title),
                message = stringResource(
                    R.string.screen_my_locations_city_data_updated_message,
                    data.cityName
                ),
                buttonText = stringResource(id = R.string.common_ok)
            )
        }

        state.appError?.let{
            ErrorDialog(
                onDialogClose = { event(MyLocationsScreenViewEvent.DismissErrorDialog) },
                appError = it
            )
        }

        SearchInputRow(
            modifier = Modifier
                .constrainAs(editText) {
                    top.linkTo(topBar.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .padding(top = if (state.isSearchInputFocused) 16.dp else 0.dp),
            searchInput = state.searchInput,
            event = event,
            hasFocus = state.isSearchInputFocused,
            onFocusChange = { event(MyLocationsScreenViewEvent.SetSearchInputFocus(it)) },
        )

        if (state.showLocationPermissionRationaleDialog) {
            PermissionDialog(
                permissionTextProvider = LocationPermissionTextProvider(),
                isPermanentlyDeclined = locationPermissionState.status.shouldShowRationale.not(),
                onDismiss = {
                    event(MyLocationsScreenViewEvent.SetLocationPermissionRationaleDialogState(false))
                },
                onOkClick = {
                    event(MyLocationsScreenViewEvent.SetLocationPermissionRationaleDialogState(false))
                    locationPermissionState.launchPermissionRequest()
                },
                onGoToAppSettingsClick = { context.openAppSettings() }
            )
        }

        AnimatedVisibility(
            modifier = Modifier
                .constrainAs(focusNoLocation) {
                    top.linkTo(editText.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            visible = state.isSearchInputFocused && state.locations.isEmpty(),
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
            modifier = Modifier
                .constrainAs(focusLocation) {
                    top.linkTo(editText.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                }
                .padding(top = 16.dp),
            visible = !state.isSearchInputFocused && state.savedCities.isNotEmpty(),
            enter = fadeIn(animationSpec = tween(delayMillis = ANIMATION_DELAY)),
            exit = fadeOut(animationSpec = tween(durationMillis = ANIMATION_DELAY))
        ) {
            SavedCities(
                cities = state.savedCities,
                onEvent = event,
                isInEditMode = state.isInEditMode,
                onMove = { from, to -> event(MyLocationsScreenViewEvent.SwapCities(from, to)) },
            )
        }

        AnimatedVisibility(
            modifier = Modifier
                .constrainAs(noFocusNoLocation) {
                    top.linkTo(editText.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            visible = !state.isSearchInputFocused && state.savedCities.isEmpty(),
            enter = fadeIn(animationSpec = tween(delayMillis = ANIMATION_DELAY)),
            exit = fadeOut(animationSpec = tween(durationMillis = ANIMATION_DELAY))
        ) {
            AddNewLocations()
        }

        AnimatedVisibility(
            modifier = Modifier
                .constrainAs(searchCityList) {
                    top.linkTo(editText.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            enter = fadeIn(animationSpec = tween(delayMillis = ANIMATION_DELAY)),
            exit = fadeOut(animationSpec = tween(durationMillis = ANIMATION_DELAY)),
            visible = state.isSearchInputFocused && state.locations.isNotEmpty()
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

@PreviewLightDark
@Composable
fun MyLocationsScreenPreview(
    @PreviewParameter(StateParameterProvider::class) state: MyLocationsScreenState
) {
    WeatherAppTheme {
        Surface(color = LocalCustomColorsPalette.current.background) {
            MyLocationsScreenContent(
                state = state,
                event = {}
            )
        }
    }
}

private class StateParameterProvider : PreviewParameterProvider<MyLocationsScreenState> {
    override val values: Sequence<MyLocationsScreenState>
        get() = sequenceOf(
            MyLocationsScreenState(),
            MyLocationsScreenState(
                isSearchInputFocused = true
            ),
            MyLocationsScreenState(
                isSearchInputFocused = true,
                searchInput = "Lon",
                locations = listOf(
                    SearchCityDetails("London", "England", "a", false),
                    SearchCityDetails("Long Beach", "California, USA", "b", false),
                    SearchCityDetails("Longview", "Texas, USA", "c", false),
                    SearchCityDetails("Londrina", "Brazil", "d", false),
                    SearchCityDetails("Longueuil", "Quebec, Canada", "e", false)
                )
            ),
            MyLocationsScreenState(
                savedCities = listOf(MockDataHelper.createCityWeather()),
                isInEditMode = false
            ),
            MyLocationsScreenState(
                savedCities = listOf(MockDataHelper.createCityWeather()),
                isInEditMode = true
            ),
        )
}
