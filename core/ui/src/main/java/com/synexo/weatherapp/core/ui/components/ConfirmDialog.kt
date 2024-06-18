package com.synexo.weatherapp.core.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.synexo.weatherapp.core.ui.LocalCustomColorsPalette
import com.synexo.weatherapp.core.ui.WeatherAppTheme

@Composable
fun ConfirmDialog(
    onDialogClose: () -> Unit,
    @StringRes titleResId: Int,
    @StringRes messageResId: Int,
    @StringRes buttonTextResId: Int
) {
    ConfirmDialog(
        onDialogClose = onDialogClose,
        title = stringResource(id = titleResId),
        message = stringResource(id = messageResId),
        buttonText = stringResource(id = buttonTextResId)
    )
}

@Composable
fun ConfirmDialog(
    onDialogClose: () -> Unit,
    title: String,
    message: String,
    buttonText: String
) {
    AlertDialog(
        containerColor = LocalCustomColorsPalette.current.cardBackground,
        onDismissRequest = onDialogClose,
        title = {
            Text(
                text = title,
                color = LocalCustomColorsPalette.current.text
            )
        },
        text = {
            Text(
                text = message,
                color = LocalCustomColorsPalette.current.text
            )
        },
        confirmButton = {
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LocalCustomColorsPalette.current.textClickable
                ),
                shape = RoundedCornerShape(8.dp),
                onClick = onDialogClose
            ) {
                Text(
                    text = buttonText,
                    color = LocalCustomColorsPalette.current.buttonText
                )
            }
        }
    )
}

@PreviewLightDark
@Composable
fun ConfirmDialogPreview() {
    WeatherAppTheme {
        Surface {
            ConfirmDialog(
                onDialogClose = {},
                title = "Title",
                message = "Message",
                buttonText = "OK"
            )
        }
    }
}