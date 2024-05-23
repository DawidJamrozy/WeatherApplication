package com.synexo.weatherapp.ui.futureHourlyForecast.components


import android.graphics.PorterDuff
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.patrykandpatrick.vico.compose.common.component.rememberShapeComponent
import com.patrykandpatrick.vico.compose.common.of
import com.patrykandpatrick.vico.compose.common.shader.color
import com.patrykandpatrick.vico.compose.common.shader.component
import com.patrykandpatrick.vico.compose.common.shader.verticalGradient
import com.patrykandpatrick.vico.compose.m3.common.rememberM3VicoTheme
import com.patrykandpatrick.vico.core.cartesian.HorizontalLayout
import com.patrykandpatrick.vico.core.cartesian.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.cartesian.data.AxisValueOverrider
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.common.Dimensions
import com.patrykandpatrick.vico.core.common.data.ExtraStore
import com.patrykandpatrick.vico.core.common.shader.DynamicShader
import com.patrykandpatrick.vico.core.common.shape.Shape
import com.synexo.weatherapp.ui.futureHourlyForecast.FutureHourlyForecastScreenState
import com.synexo.weatherapp.ui.theme.LocalCustomColorsPalette
import com.synexo.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun HumidityVicoLineChart(
    modifier: Modifier,
    title: String,
    state: FutureHourlyForecastScreenState,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier.padding(10.dp),
            text = "$title (%)"
        )

        ProvideVicoTheme(
            theme = rememberM3VicoTheme(
                textColor = LocalCustomColorsPalette.current.text,
            )
        ) {
            CartesianChartHost(
                //scrollState = scrollState,
                chart = rememberCartesianChart(
                    rememberLineCartesianLayer(
                        axisValueOverrider = AxisValueOverrider.fixed(
                            minY = state.minHumidity,
                            maxY = state.maxHumidity
                        ),
                        spacing = 50.dp,
                        lines = listOf(
                            rememberLineSpec(
                                shader = DynamicShader.color(Color.Blue),
                                backgroundShader = DynamicShader.compose(
                                    DynamicShader.component(
                                        componentSize = 6.dp,
                                        component =
                                        rememberShapeComponent(
                                            shape = Shape.Pill,
                                            color = Color.Blue,
                                            margins = Dimensions.of(1.dp),
                                        ),
                                    ),
                                    DynamicShader.verticalGradient(
                                        arrayOf(Color.Black, Color.Transparent),
                                    ),
                                    PorterDuff.Mode.DST_IN,
                                ),
                            )
                        ),
                    ),
                    startAxis = rememberStartAxis(
                        itemPlacer = remember { AxisItemPlacer.Vertical.step(step = { 20f }) }
                    ),
                    bottomAxis = rememberBottomAxis(
                        guideline = null,
                        valueFormatter = { x, chartValues, _ ->
                            chartValues.model.extraStore[state.labelListKey][x.toInt()]
                        }
                    ),
                ),
                modelProducer = state.humidityModelProducer,
                modifier = modifier,
                horizontalLayout = HorizontalLayout.fullWidth(),
                zoomState = rememberVicoZoomState(zoomEnabled = false),
            )
        }
    }
}

@PreviewLightDark
@Composable
fun HumidityVicoLineChartPreview() {
    val labelListKey: ExtraStore.Key<List<String>> = ExtraStore.Key()
    val xLabels:List<String> = listOf("00:00", "02:00", "03:00", "04:00", "05:00", "06:00")
    val model = CartesianChartModelProducer.build {
        lineSeries { series(70,80, 40, 50, 90, 85) }
        updateExtras { it[labelListKey] = xLabels }
    }
    WeatherAppTheme {
        Surface(color = LocalCustomColorsPalette.current.background) {
            HumidityVicoLineChart(
                modifier = Modifier,
                title = "Humidity",
                state = FutureHourlyForecastScreenState(
                    labelListKey = labelListKey,
                    humidityModelProducer = model
                ),
            )
        }
    }
}
