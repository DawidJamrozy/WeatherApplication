package com.synexo.weatherapp.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Immutable
data class CustomColorsPalette(
    val text: Color = Color.Unspecified,
    val textReverse: Color = Color.Unspecified,
    val icon: Color = Color.Unspecified,
    val iconClickable: Color = Color.Unspecified,
    val textSecondary: Color = Color.Unspecified,
    val blockedText: Color = Color.Unspecified,
    val background: Color = Color.Unspecified,
    val contextBackground: Color = Color.Unspecified,
    val navigationBackground: Color = Color.Unspecified,
    val textClickable: Color = Color.Unspecified,
    val inputTextLabelIcon: Color = Color.Unspecified,
    val inputTextGradientStart: Color = Color.Unspecified,
    val cardBackground: Color = Color.Unspecified,
    val buttonText: Color = Color.Unspecified,
    val buttonBackground: Color = Color.Unspecified,
    val bottomBarUnselectedNavigationItem: Color = Color.Unspecified,
    val bottomBarSelectedNavigationItem: Color = Color.Unspecified,
    val cardBackgroundGradient: Brush = Brush.horizontalGradient(listOf())
)

val LocalCustomColorsPalette = staticCompositionLocalOf { CustomColorsPalette() }

val OnLightCustomColorsPalette = CustomColorsPalette(
    text = HeiSeBlack,
    textReverse = Color.White,
    textSecondary = LilacFields,
    blockedText = LilacFields,
    textClickable = HaileyBlue,
    inputTextLabelIcon = LilacFields,
    background = Mischka,
    cardBackground = Color.White,
    navigationBackground = WhiteSmoke,
    buttonText = Color.White,
    bottomBarSelectedNavigationItem = HaileyBlue,
    bottomBarUnselectedNavigationItem = LilacFields,
    contextBackground = Color.White,
    buttonBackground = HaileyBlue,
    icon = LilacFields,
    iconClickable = HaileyBlue,
    cardBackgroundGradient = Brush.horizontalGradient(colors = listOf(Color.White, Color.White))
)

val OnDarkCustomColorsPalette = CustomColorsPalette(
    text = Color.White,
    textReverse = HeiSeBlack,
    textSecondary = LupineBlue,
    blockedText = LilacFields,
    textClickable = SunflowerMango,
    inputTextLabelIcon = LupineBlue,
    navigationBackground = BlueWhale,
    background = BigStone,
    cardBackground = Ebony,
    buttonText = HeiSeBlack,
    contextBackground = BlueZodiac,
    bottomBarSelectedNavigationItem = SunflowerMango,
    bottomBarUnselectedNavigationItem = LupineBlue,
    buttonBackground = SunflowerMango,
    icon = LupineBlue,
    iconClickable = SunflowerMango,
    cardBackgroundGradient = Brush.horizontalGradient(colors = listOf(NileBlue, BlueZodiac))
)
