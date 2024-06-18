package com.synexo.weatherapp.settings.presentation.components
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.synexo.weatherapp.core.ui.LocalCustomColorsPalette
import com.synexo.weatherapp.core.ui.WeatherAppTheme

@Composable
fun SwitchButton(
    leftText: String,
    rightText: String,
    onClick: (Boolean) -> Unit,
    height: Dp = 30.dp,
    width: Dp = 120.dp,
    backgroundColor:Color = LocalCustomColorsPalette.current.contextBackground,
    borderColor:Color = LocalCustomColorsPalette.current.textClickable,
    highLightedColor:Color = LocalCustomColorsPalette.current.textClickable,
    borderWidth: Dp = 1.dp,
    cornerRadius: Dp = 5.dp,
    fontSize: TextUnit = 12.sp,
    isToogled: Boolean = false
) {
    val density = LocalDensity.current
    val externalShape = RoundedCornerShape(cornerRadius)

    val interactionSource = remember { MutableInteractionSource() }
    val selectedTextColor =
        animateColorAsState(if (isToogled) highLightedColor else backgroundColor)
    val notSelectedTextColor =
        animateColorAsState(if (isToogled) backgroundColor else highLightedColor)

    val animatedBoxPosition by animateIntOffsetAsState(
        targetValue = if (isToogled) {
            with(density) { IntOffset(width.div(2).toPx().toInt(), 0) }
        } else {
            IntOffset.Zero
        }
    )

    val leftCornerRadius = animateDpAsState(if (isToogled) 0.dp else cornerRadius)
    val rightCornerRadius = animateDpAsState(if (isToogled) cornerRadius else 0.dp)

    val internalShape = RoundedCornerShape(
        topStart = leftCornerRadius.value,
        bottomStart = leftCornerRadius.value,
        topEnd = rightCornerRadius.value,
        bottomEnd = rightCornerRadius.value
    )

    Box(
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { onClick(!isToogled) }
            )
            .width(width)
            .border(borderWidth, borderColor, externalShape)
            .background(shape = externalShape, color = backgroundColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(height)
                .offset { animatedBoxPosition }
                .background(shape = internalShape, color = highLightedColor)
        )
        Row(
            modifier = Modifier.height(height),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Text(
                    text = leftText,
                    fontSize = fontSize,
                    color = selectedTextColor.value
                )
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Text(
                    fontSize = fontSize,
                    text = rightText,
                    color = notSelectedTextColor.value
                )
            }
        }
    }
}

@Composable
@PreviewLightDark
fun SwitchButtonPreview() {
    WeatherAppTheme {
        Surface(color = LocalCustomColorsPalette.current.background) {
            SwitchButton(
                leftText = "°C",
                rightText = "°F",
                onClick = { },
                isToogled = true
            )
        }
    }
}