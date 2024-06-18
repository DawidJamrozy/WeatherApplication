package com.synexo.weatherapp.search.presentation.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.synexo.weatherapp.core.ui.LocalCustomColorsPalette
import com.synexo.weatherapp.search.presentation.R
import com.synexo.weatherapp.search.presentation.SearchScreenState
import com.synexo.weatherapp.search.presentation.SearchScreenViewEvent

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SearchInput(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    focusRequester: FocusRequester,
    state: SearchScreenState,
    event: (SearchScreenViewEvent) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    with(sharedTransitionScope) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .sharedElement(
                        state = rememberSharedContentState(key = "TEST"),
                        animatedVisibilityScope = animatedVisibilityScope,
                    )
                    .clip(RoundedCornerShape(8.dp))
                    .background(LocalCustomColorsPalette.current.cardBackgroundGradient)
            ) {
                TextField(
                    modifier = Modifier
                        .focusRequester(focusRequester),
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

                    value = state.searchInput,
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Search,
                            contentDescription = null,
                            tint = LocalCustomColorsPalette.current.inputTextLabelIcon
                        )
                    },
                    trailingIcon = {
                        if (state.searchInput.isNotBlank()) {
                            Icon(
                                modifier = Modifier.clickable {
                                    event(SearchScreenViewEvent.ClearSearchInput)
                                },
                                imageVector = Icons.Filled.Clear,
                                contentDescription = null,
                                tint = LocalCustomColorsPalette.current.inputTextLabelIcon
                            )
                        }
                    },
                    onValueChange = { value ->
                        event(SearchScreenViewEvent.SearchInputChanged(value))
                    },
                    placeholder = {
                        Text(
                            stringResource(R.string.screen_search_find_location),
                            color = LocalCustomColorsPalette.current.inputTextLabelIcon,
                            fontSize = 14.sp,
                            lineHeight = 14.sp
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.size(16.dp))

            Text(
                modifier = Modifier
                    .sharedElement(
                        state = rememberSharedContentState(key = "TEST_2"),
                        animatedVisibilityScope = animatedVisibilityScope,
                    )
                    .clip(CircleShape)
                    .align(Alignment.CenterVertically)
                    .wrapContentSize()
                    .clickable(
                        interactionSource = interactionSource,
                        indication = ripple(bounded = false),
                    ) {
                        event(SearchScreenViewEvent.CancelLocationSearch)
                    }
                    .padding(16.dp),
                maxLines = 1,
                color = LocalCustomColorsPalette.current.textClickable,
                text = stringResource(com.synexo.weatherapp.core.ui.R.string.common_cancel),
            )
        }
    }
}