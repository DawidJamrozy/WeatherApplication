package com.synexo.weatherapp.ui.futureHourlyForecast.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStartAxis
import com.patrykandpatrick.vico.compose.cartesian.fullWidth
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineSpec
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoZoomState
import com.patrykandpatrick.vico.compose.common.ProvideVicoTheme
import com.patrykandpatrick.vico.compose.common.shader.color
import com.patrykandpatrick.vico.compose.m3.common.rememberM3VicoTheme
import com.patrykandpatrick.vico.core.cartesian.HorizontalLayout
import com.patrykandpatrick.vico.core.cartesian.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.cartesian.data.AxisValueOverrider
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.common.data.ExtraStore
import com.patrykandpatrick.vico.core.common.shader.DynamicShader
import com.synexo.weatherapp.R
import com.synexo.weatherapp.ui.futureHourlyForecast.FutureHourlyForecastScreenState
import com.synexo.weatherapp.ui.theme.LocalCustomColorsPalette
import com.synexo.weatherapp.ui.theme.Sun
import com.synexo.weatherapp.ui.theme.WeatherAppTheme


@Composable
fun TempVicoLineChart(
    modifier: Modifier,
    title: String,
    state: FutureHourlyForecastScreenState,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = "$title (°${state.weatherUnits.degrees.name})"
        )

        ProvideVicoTheme(
            theme = rememberM3VicoTheme(
                textColor = LocalCustomColorsPalette.current.text,
            )
        ) {
            CartesianChartHost(
                chart = rememberCartesianChart(
                    rememberLineCartesianLayer(
                        axisValueOverrider = AxisValueOverrider.fixed(
                            minY = state.minTemp,
                            maxY = state.maxTemp
                        ),
                        spacing = 50.dp,
                        lines = listOf(
                            rememberLineSpec(shader = DynamicShader.color(Sun)),
                            rememberLineSpec(shader = DynamicShader.color(Color.Blue))
                        ),
                    ),
                    legend = rememberLegend(
                        chartColors = listOf(Sun, Color.Blue),
                        titles = listOf(
                            stringResource(id = R.string.screen_hourly_forecast_measured),
                            stringResource(id = R.string.screen_hourly_forecast_feels_like)
                        )
                    ),
                    startAxis = rememberStartAxis(
                        itemPlacer = remember { AxisItemPlacer.Vertical.step(step = { 2f }) }
                    ),
                    bottomAxis = rememberBottomAxis(
                        guideline = null,
                        valueFormatter = { x, chartValues, _ ->
                            chartValues.model.extraStore[state.labelListKey][x.toInt()]
                        }
                    ),
                ),
                modelProducer = state.tempModelProducer,
                modifier = modifier,
                horizontalLayout = HorizontalLayout.fullWidth(),
                zoomState = rememberVicoZoomState(zoomEnabled = false),
            )
        }
    }
}

@PreviewLightDark
@Composable
fun TempVicoLineChartPreview() {
    val labelListKey: ExtraStore.Key<List<String>> = ExtraStore.Key()
    val xLabels:List<String> = listOf("00:00", "02:00", "03:00", "04:00", "05:00", "06:00")
    val model = CartesianChartModelProducer.build {
        lineSeries { series(1, 2, 4, 3, 2, 3,) }
        updateExtras { it[labelListKey] = xLabels }
    }
    WeatherAppTheme {
        Surface(color = LocalCustomColorsPalette.current.background) {
            TempVicoLineChart(
                modifier = Modifier,
                title = "Temperature",
                state = FutureHourlyForecastScreenState(
                    minTemp = 0f,
                    maxTemp = 10f,
                    labelListKey = labelListKey,
                    tempModelProducer = model
                ),
            )
        }
    }
}
