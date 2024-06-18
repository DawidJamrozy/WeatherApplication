package com.synexo.weatherapp.myLocations.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.synexo.weatherapp.core.model.MockDataHelper
import com.synexo.weatherapp.core.ui.LocalCustomColorsPalette
import com.synexo.weatherapp.core.ui.LocalNavAnimatedVisibilityScope
import com.synexo.weatherapp.core.ui.LocalSharedTransitionScope
import com.synexo.weatherapp.core.ui.WeatherAppTheme
import com.synexo.weatherapp.core.ui.components.AcceptRejectDialog
import com.synexo.weatherapp.core.ui.components.CenteredProgress
import com.synexo.weatherapp.core.ui.components.ConfirmDialog
import com.synexo.weatherapp.core.ui.components.ErrorDialog
import com.synexo.weatherapp.core.ui.safeNavigate
import com.synexo.weatherapp.core.ui.util.NavRoutes
import com.synexo.weatherapp.core.ui.viewModel.CityWeatherSharedViewModel
import com.synexo.weatherapp.core.ui.viewModel.rememberViewEvent
import com.synexo.weatherapp.myLocations.presentation.Const.ANIMATION_DELAY
import com.synexo.weatherapp.myLocations.presentation.components.AddNewLocations
import com.synexo.weatherapp.myLocations.presentation.components.DisabledSearchBar
import com.synexo.weatherapp.myLocations.presentation.components.MyLocationsTopBar
import com.synexo.weatherapp.myLocations.presentation.components.SavedCities
import com.synexo.weatherapp.core.ui.R as CoreR

private object Const {
    const val ANIMATION_DELAY = 250
}

@ExperimentalSharedTransitionApi
@Composable
fun MyLocationsScreen(
    navController: NavController,
    sharedStateViewModel: CityWeatherSharedViewModel,
    cityName: String?,
    viewModel: MyLocationsScreenViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()
    val navigationEvent by viewModel.viewModelEvent.collectAsState()

    if(cityName != null) {
        LaunchedEffect(Unit) {
            viewModel.onViewEvent(MyLocationsScreenViewEvent.CityUpdated(cityName))
            navController.currentBackStackEntry?.savedStateHandle?.remove<String>("resultKey")
        }
    }

    navigationEvent
        ?.getContentIfNotHandled()
        ?.let { event ->
            when (event) {
                is MyLocationsScreenViewModelEvent.NavigateToAddCityWeather -> {
                    sharedStateViewModel.updateState(event.cityWeather)
                    navController.safeNavigate(NavRoutes.AddCityWeatherScreen.route)
                }

                is MyLocationsScreenViewModelEvent.NavigateToSearchScreen -> {
                    navController.safeNavigate(NavRoutes.SearchScreen.route)
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

@ExperimentalSharedTransitionApi
@Composable
fun MyLocationsScreenContent(
    state: MyLocationsScreenState,
    event: (MyLocationsScreenViewEvent) -> Unit
) {
    var showScreenA by remember { mutableStateOf(false) }
    val sharedTransitionScope = LocalSharedTransitionScope.current
        ?: throw IllegalStateException("No shared scope found")
    val animatedVisibilityScope = LocalNavAnimatedVisibilityScope.current
        ?: throw IllegalStateException("No animated scope found")

    LaunchedEffect(Unit) {
        showScreenA = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        AnimatedVisibility(
            modifier = Modifier,
            visible = showScreenA,
            enter = topBarEnterAnimation(),
            exit = topBarExitAnimation()
        ) {
            println(state.savedCities.isNotEmpty())
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
                acceptButtonText = stringResource(id = CoreR.string.common_yes),
                rejectButtonText = stringResource(id = CoreR.string.common_no),
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
                buttonText = stringResource(id = CoreR.string.common_ok)
            )
        }

        state.appError?.let {
            ErrorDialog(
                onDialogClose = { event(MyLocationsScreenViewEvent.DismissErrorDialog) },
                appError = it
            )
        }

        DisabledSearchBar(
            modifier = Modifier
                .fillMaxWidth(),
            event = {
                showScreenA = !showScreenA
                event(it)
            },
            sharedAnimatedVisibilityScope = animatedVisibilityScope,
            sharedTransitionScope = sharedTransitionScope
        )

        if(state.isLoading) {
            CenteredProgress()
        }

        AnimatedVisibility(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp),
            visible = !state.isLoading && showScreenA && state.savedCities.isNotEmpty(),
            enter = enterAnimation(),
            exit = exitAnimation()
        ) {
            SavedCities(
                cities = state.savedCities,
                onEvent = event,
                isInEditMode = state.isInEditMode,
                onMove = { from, to -> event(MyLocationsScreenViewEvent.SwapCities(from, to)) },
            )
        }

        AnimatedVisibility(
            modifier = Modifier,
            visible = !state.isLoading && showScreenA && state.savedCities.isEmpty(),
            enter = enterAnimation(),
            exit = exitAnimation()
        ) {
            AddNewLocations()
        }
    }
}

private fun topBarEnterAnimation() =
    fadeIn(animationSpec = tween(durationMillis = ANIMATION_DELAY)) +
            slideInVertically(
                initialOffsetY = { -it },
                animationSpec = tween(durationMillis = ANIMATION_DELAY)
            )

private fun topBarExitAnimation() =
    fadeOut(animationSpec = tween(durationMillis = ANIMATION_DELAY)) +
            slideOutVertically(
                targetOffsetY = { -it },
                animationSpec = tween(durationMillis = ANIMATION_DELAY)
            )

private fun enterAnimation() = fadeIn(animationSpec = tween(durationMillis = ANIMATION_DELAY)) +
        slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(durationMillis = ANIMATION_DELAY)
        )

private fun exitAnimation() = fadeOut(animationSpec = tween(durationMillis = ANIMATION_DELAY)) +
        slideOutVertically(
            targetOffsetY = { it },
            animationSpec = tween(durationMillis = ANIMATION_DELAY)
        )

@OptIn(ExperimentalSharedTransitionApi::class)
@PreviewLightDark
@Composable
fun MyLocationsScreenPreview(
    @PreviewParameter(StateParameterProvider::class) state: MyLocationsScreenState
) {
    WeatherAppTheme {
        Surface(color = LocalCustomColorsPalette.current.background) {
                MyLocationsScreenContent(
                    state = state,
                    event = {},
                )
        }
    }
}

private class StateParameterProvider : PreviewParameterProvider<MyLocationsScreenState> {
    override val values: Sequence<MyLocationsScreenState>
        get() = sequenceOf(
            MyLocationsScreenState(),
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
