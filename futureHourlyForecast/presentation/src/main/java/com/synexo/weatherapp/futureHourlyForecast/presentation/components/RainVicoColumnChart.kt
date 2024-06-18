package com.synexo.weatherapp.futureHourlyForecast.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStartAxis
import com.patrykandpatrick.vico.compose.cartesian.fullWidth
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoZoomState
import com.patrykandpatrick.vico.compose.common.ProvideVicoTheme
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.m3.common.rememberM3VicoTheme
import com.patrykandpatrick.vico.core.cartesian.HorizontalLayout
import com.patrykandpatrick.vico.core.cartesian.data.AxisValueOverrider
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.core.common.data.ExtraStore
import com.synexo.weatherapp.core.ui.LocalCustomColorsPalette
import com.synexo.weatherapp.core.ui.WeatherAppTheme


@Composable
fun RainVicoColumnChart(
    modifier: Modifier,
    title: String,
    state: com.synexo.weatherapp.futureHourlyForecast.presentation.FutureHourlyForecastScreenState,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = "$title (mm/h)"
        )

        ProvideVicoTheme(
            theme = rememberM3VicoTheme(
                textColor = LocalCustomColorsPalette.current.text,
            )
        ) {
            CartesianChartHost(
                //scrollState = scrollState,
                chart = rememberCartesianChart(
                    rememberColumnCartesianLayer(
                        columnProvider = ColumnCartesianLayer.ColumnProvider.series(
                            rememberLineComponent(
                                color = Color(0xffff5500),
                                thickness = 32.dp,
                            ),
                        ),
                        axisValueOverrider = AxisValueOverrider.fixed(
                            minY = state.minRain,
                            maxY = state.maxRain
                        ),
                    ),
                    startAxis = rememberStartAxis(),
                    bottomAxis = rememberBottomAxis(
                        guideline = null,
                        valueFormatter = { x, chartValues, _ ->
                            chartValues.model.extraStore[state.labelListKey][x.toInt()]
                        },
                    ),
                ),
                modelProducer = state.rainModelProducer,
                modifier = modifier,
                horizontalLayout = HorizontalLayout.fullWidth(),
                zoomState = rememberVicoZoomState(zoomEnabled = false),
            )
        }
    }
}

@PreviewLightDark
@Composable
fun RainVicoColumnChartPreview() {
    val labelListKey: ExtraStore.Key<List<String>> = ExtraStore.Key()
    val xLabels:List<String> = listOf("00:", "02:00", "03:00", "04:00", "05:00", "06:00")
    val model = CartesianChartModelProducer.build {
        columnSeries { series(1, 2, 4, 3, 2, 3) }
        updateExtras { it[labelListKey] = xLabels }
    }
    WeatherAppTheme {
        Surface(color = LocalCustomColorsPalette.current.background) {
            RainVicoColumnChart(
                modifier = Modifier,
                title = "Rain",
                state = com.synexo.weatherapp.futureHourlyForecast.presentation.FutureHourlyForecastScreenState(
                    minRain = 0f,
                    maxRain = 10f,
                    labelListKey = labelListKey,
                    rainModelProducer = model
                ),
            )
        }
    }
}
