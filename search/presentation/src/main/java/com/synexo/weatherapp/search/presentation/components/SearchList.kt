package com.synexo.weatherapp.search.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.synexo.weatherapp.core.ui.LocalCustomColorsPalette
import com.synexo.weatherapp.core.ui.WeatherAppTheme
import com.synexo.weatherapp.search.presentation.SearchScreenViewEvent
import com.synexo.weatherapp.search.presentation.model.SearchCityDetails
import com.synexo.weatherapp.core.ui.R as CoreR

@Composable
fun SearchList(
    locations: List<SearchCityDetails>,
    savedCities: List<String>,
    searchInput: String,
    isLoading: Boolean,
    onEvent: (SearchScreenViewEvent) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(vertical = 10.dp)
    ) {
        items(locations) { location ->
            val disableRow = savedCities.contains(location.placeId)
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(LocalCustomColorsPalette.current.cardBackgroundGradient)
                        .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .padding(top = 6.dp, bottom = 6.dp, end = 10.dp),
                        text = if (disableRow) {
                            buildAnnotatedString { append(location.getFullName()) }
                        } else {
                            highlightText(
                                primaryText = location.primaryName,
                                secondaryText = location.secondaryName,
                                input = searchInput,
                                highlightColor = LocalCustomColorsPalette.current.textClickable,
                                nonHighlightColor = LocalCustomColorsPalette.current.text,
                                disabledColor = LocalCustomColorsPalette.current.inputTextLabelIcon,
                                fontSize = 14.sp
                            )
                        },
                        fontSize = 14.sp,
                        color = LocalCustomColorsPalette.current.inputTextLabelIcon,
                    )

                    Button(
                        onClick = {
                            keyboardController?.hide()
                            onEvent(SearchScreenViewEvent.OnCityClicked(location))
                        },
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .wrapContentSize()
                            .height(35.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LocalCustomColorsPalette.current.textClickable
                        ),
                        shape = RoundedCornerShape(16.dp),
                        enabled = !disableRow && !isLoading && !location.isLoading
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            if (location.isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    strokeWidth = 3.dp,
                                    color = LocalCustomColorsPalette.current.textClickable
                                )
                            } else {
                                Text(
                                    text = stringResource(id = CoreR.string.common_add),
                                    color = LocalCustomColorsPalette.current.textReverse,
                                    fontSize = 14.sp,
                                    lineHeight = 14.sp
                                )
                            }
                            // Transparent text to maintain width
                            Text(
                                text = stringResource(id = CoreR.string.common_add),
                                fontSize = 14.sp,
                                lineHeight = 14.sp,
                                color = Color.Transparent
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@PreviewLightDark
@Composable
fun SearchListPreview(
    @PreviewParameter(SearchListPreviewParameterProvider::class) isLoading: Boolean
) {
    WeatherAppTheme {
        Surface(color = LocalCustomColorsPalette.current.background) {
            SearchList(
                locations = listOf(
                    SearchCityDetails(
                        "London",
                        "England",
                        "a",
                        true
                    ),
                    SearchCityDetails(
                        "Long Beach",
                        "California, USA",
                        "b",
                        false
                    ),
                    SearchCityDetails(
                        "Longview",
                        "Texas, USA",
                        "c",
                        false
                    ),
                    SearchCityDetails(
                        "Londrina",
                        "Brazil",
                        "d",
                        false
                    ),
                    SearchCityDetails(
                        "Longueuil",
                        "Quebec, Canada",
                        "e",
                        false
                    )

                ),
                savedCities = listOf("a"),
                searchInput = "Long",
                isLoading = isLoading,
                onEvent = { }
            )
        }
    }
}


private class SearchListPreviewParameterProvider : PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean>
        get() = sequenceOf(true, false)
}
