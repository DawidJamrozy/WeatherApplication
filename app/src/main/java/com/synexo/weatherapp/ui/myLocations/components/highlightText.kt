package com.synexo.weatherapp.ui.myLocations.components

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.synexo.weatherapp.ui.theme.LocalCustomColorsPalette
import com.synexo.weatherapp.ui.theme.WeatherAppTheme
import java.text.Normalizer

@Composable
fun highlightText(
    primaryText: String,
    secondaryText: String,
    input: String,
    highlightColor: Color,
    nonHighlightColor: Color,
    disabledColor: Color,
    fontSize: TextUnit = 16.sp
): AnnotatedString {
    val normalizedInput = normalizeText(input)
    val normalizedText = normalizeText(primaryText)

    val (startIndex, endIndex) = findSubstringIndices(normalizedText, normalizedInput)
        ?: return buildAnnotatedString {
            withStyle(style = SpanStyle(color = nonHighlightColor, fontSize = fontSize)) {
                append(primaryText)
            }
            withStyle(style = SpanStyle(color = disabledColor, fontSize = fontSize)) {
                append(", $secondaryText")
            }
        }

    return buildAnnotatedString {
        withStyle(style = SpanStyle(color = nonHighlightColor, fontSize = fontSize)) {
            append(primaryText.substring(0, startIndex))
        }
        withStyle(style = SpanStyle(color = highlightColor, fontSize = fontSize)) {
            append(primaryText.substring(startIndex, endIndex + 1))
        }
        withStyle(style = SpanStyle(color = nonHighlightColor, fontSize = fontSize)) {
            append(primaryText.substring(endIndex + 1, primaryText.length))
        }
        withStyle(style = SpanStyle(color = disabledColor, fontSize = fontSize)) {
            append(", $secondaryText")
        }
    }
}

private fun findSubstringIndices(text: String, input: String): Pair<Int, Int>? {
    val startIndex = text.indexOf(input)
    return if (startIndex >= 0) {
        // End index is calculated by adding the length of the input to the start index
        // and subtracting 1 since indices are 0-based.
        val endIndex = startIndex + input.length - 1
        Pair(startIndex, endIndex)
    } else {
        // Return null if the input is not found within the text
        null
    }
}

private fun normalizeText(input: String): String {
    // Normalize including decomposing accented characters
    val decomposed = Normalizer.normalize(input, Normalizer.Form.NFD)

    // Remove diacritics (accent marks) and non-ASCII characters if necessary
    val nonDiacritics = decomposed.replace("\\p{Mn}+".toRegex(), "")

    // Further normalization steps (lowercasing, removing punctuation, etc.) can be applied here
    return nonDiacritics.lowercase().filter { it.isLetterOrDigit() || it.isWhitespace() }
}

@PreviewLightDark
@Composable
private fun HighlightTextPreview() {
    WeatherAppTheme {
        Surface(color = LocalCustomColorsPalette.current.background) {
            Text(text =
                highlightText(
                    primaryText = "San Francisco",
                    secondaryText = "California, USA",
                    input = "San",
                    highlightColor = LocalCustomColorsPalette.current.textClickable,
                    nonHighlightColor = LocalCustomColorsPalette.current.text,
                    disabledColor = LocalCustomColorsPalette.current.inputTextLabelIcon,
                )
            )
        }
    }
}