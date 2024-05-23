package com.synexo.weatherapp.ui.myLocations.components


import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.synexo.weatherapp.R
import com.synexo.weatherapp.ui.myLocations.MyLocationsScreenViewEvent
import com.synexo.weatherapp.ui.theme.LocalCustomColorsPalette
import com.synexo.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun SearchInputRow(
    searchInput: String,
    event: (MyLocationsScreenViewEvent) -> Unit,
    onFocusChange: (Boolean) -> Unit,
    hasFocus: Boolean,
    modifier: Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val focusManager = LocalFocusManager.current
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val animatedWidth by animateDpAsState(
        label = "",
        targetValue = if (hasFocus || searchInput.isNotEmpty()) screenWidth * 0.7f else screenWidth,
        animationSpec = tween(500)
    )

    LaunchedEffect(key1 = hasFocus) {
        if(!hasFocus) focusManager.clearFocus()
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .width(animatedWidth)
                .clip(RoundedCornerShape(8.dp))
                .background(LocalCustomColorsPalette.current.cardBackgroundGradient),
        ) {
            TextField(
                modifier = Modifier
                    .onFocusChanged { focusState ->
                        onFocusChange(focusState.hasFocus)
                    },
                textStyle = TextStyle(
                    color = LocalCustomColorsPalette.current.text,
                    fontSize = 14.sp,
                    lineHeight = 14.sp
                ),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    cursorColor = LocalCustomColorsPalette.current.textClickable,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                value = searchInput,
                leadingIcon = {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = null,
                        tint = LocalCustomColorsPalette.current.inputTextLabelIcon
                    )
                },
                trailingIcon = {
                    if (searchInput.isNotBlank()) {
                        Icon(
                            modifier = Modifier.clickable {
                                event(MyLocationsScreenViewEvent.ClearSearchInput)
                            },
                            imageVector = Icons.Filled.Clear,
                            contentDescription = null,
                            tint = LocalCustomColorsPalette.current.inputTextLabelIcon
                        )
                    }
                },
                onValueChange = { value ->
                    event(MyLocationsScreenViewEvent.SearchInputChanged(value))
                },
                placeholder = {
                    Text(
                        stringResource(R.string.screen_my_locations_find_location),
                        color = LocalCustomColorsPalette.current.inputTextLabelIcon,
                        fontSize = 14.sp,
                        lineHeight = 14.sp
                    )
                }
            )
        }

        Text(
            modifier = Modifier
                .clip(CircleShape)
                .align(Alignment.CenterVertically)
                .wrapContentSize()
                .clickable(
                    interactionSource = interactionSource,
                    indication = rememberRipple(bounded = false),
                ) {
                    focusManager.clearFocus()
                    onFocusChange(false)
                    event(MyLocationsScreenViewEvent.CancelLocationSearch)
                }
                .padding(12.dp),
            maxLines = 1,
            color = LocalCustomColorsPalette.current.textClickable,
            text = stringResource(R.string.screen_my_locations_cancel),
        )
    }
}

@PreviewLightDark
@Composable
private fun SearchInputRowPreview(
    @PreviewParameter(SearchInputRowStateParameterProvider::class) state: PreviewProviderState
) {
    WeatherAppTheme {
        Surface(color = LocalCustomColorsPalette.current.background) {
            SearchInputRow(
                searchInput = state.input,
                onFocusChange = {},
                event = {},
                hasFocus = state.hasFocus,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

private class PreviewProviderState(
    val hasFocus: Boolean,
    val input: String
)

private class SearchInputRowStateParameterProvider :
    PreviewParameterProvider<PreviewProviderState> {
    override val values: Sequence<PreviewProviderState>
        get() = sequenceOf(
            PreviewProviderState(
                hasFocus = true,
                input = "Rzeszów"
            ),
            PreviewProviderState(
                hasFocus = false,
                input = ""
            )
        )
}

