package com.synexo.weatherapp.myLocations.presentation.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.synexo.weatherapp.core.ui.LocalCustomColorsPalette
import com.synexo.weatherapp.myLocations.presentation.MyLocationsScreenViewEvent
import com.synexo.weatherapp.myLocations.presentation.R

@ExperimentalSharedTransitionApi
@Composable
fun DisabledSearchBar(
    modifier: Modifier,
    sharedTransitionScope: SharedTransitionScope,
    sharedAnimatedVisibilityScope: AnimatedVisibilityScope,
    event: (MyLocationsScreenViewEvent) -> Unit,
) {
    val mis = remember { MutableInteractionSource() }
    with(sharedTransitionScope) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .sharedElement(
                        state = rememberSharedContentState(key = "TEST"),
                        animatedVisibilityScope = sharedAnimatedVisibilityScope,
                    )
                    .clickable(
                        interactionSource = mis,
                        indication = null // Disables the ripple effect
                    ) {
                        event(MyLocationsScreenViewEvent.GoToSearchScreen)
                    }
                    .clip(RoundedCornerShape(8.dp))
                    .background(LocalCustomColorsPalette.current.cardBackgroundGradient)

            ) {
                TextField(
                    textStyle = TextStyle(
                        color = LocalCustomColorsPalette.current.text,
                        fontSize = 14.sp,
                        lineHeight = 14.sp
                    ),
                    enabled = false,
                    readOnly = true,
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        cursorColor = LocalCustomColorsPalette.current.textClickable,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    value = "",
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Search,
                            contentDescription = null,
                            tint = LocalCustomColorsPalette.current.inputTextLabelIcon
                        )
                    },
                    onValueChange = { _ -> },
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
                    .sharedElement(
                        state = rememberSharedContentState(key = "TEST_2"),
                        animatedVisibilityScope = sharedAnimatedVisibilityScope,
                    )
                    .clip(CircleShape)
                    .align(Alignment.CenterVertically)
                    .wrapContentSize()
                    .padding(top = 16.dp, bottom = 16.dp, end = 16.dp),
                maxLines = 1,
                color = LocalCustomColorsPalette.current.textClickable,
                text = stringResource(R.string.screen_my_locations_cancel),
            )
        }
    }
}
