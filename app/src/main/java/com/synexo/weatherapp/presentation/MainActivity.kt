package com.synexo.weatherapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
import com.synexo.weatherapp.core.domain.usecase.RefreshAllWeatherDataUseCase
import com.synexo.weatherapp.core.model.Theme
import com.synexo.weatherapp.core.ui.WeatherAppTheme
import com.synexo.weatherapp.settings.domain.usecase.GetAppSettingsUseCase
import com.synexo.weatherapp.util.disableTabRowMinWidth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var getAppSettingsUseCase: GetAppSettingsUseCase

    @Inject
    lateinit var refreshAllWeatherDataUseCase: RefreshAllWeatherDataUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        refreshWeatherData()
        disableTabRowMinWidth()
        setContent {
            val resource by getAppSettingsUseCase
                .invoke(Unit)
                .collectAsState(initial = null)

            resource
                ?.appSettings
                ?.let { weatherSettings ->
                    val darkTheme = when (weatherSettings.theme) {
                        is Theme.Light -> false
                        is Theme.Dark -> true
                        is Theme.System -> isSystemInDarkTheme()
                    }

                    WeatherAppTheme(darkTheme, false) {
                        MyApp()
                    }
                }
        }
    }

    private fun refreshWeatherData() {
        lifecycleScope.launch {
            refreshAllWeatherDataUseCase.invoke(Unit)
        }
    }
}
