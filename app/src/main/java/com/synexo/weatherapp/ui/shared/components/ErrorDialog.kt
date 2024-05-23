package com.synexo.weatherapp.ui.shared.components


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
import com.synexo.weatherapp.R
import com.synexo.weatherapp.core.AppError
import com.synexo.weatherapp.ui.theme.LocalCustomColorsPalette
import com.synexo.weatherapp.ui.theme.WeatherAppTheme
import com.synexo.weatherapp.util.extensions.getErrorMessage

@Composable
fun ErrorDialog(
    onDialogClose: () -> Unit,
    appError: AppError,
) {
    AlertDialog(
        containerColor = LocalCustomColorsPalette.current.cardBackground,
        onDismissRequest = onDialogClose,
        title = {
            Text(
                text = stringResource(id = R.string.error_title),
                color = LocalCustomColorsPalette.current.text
            )
        },
        text = {
            Text(
                text = stringResource(id = appError.getErrorMessage()),
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
                    text = stringResource(id = R.string.common_ok),
                    color = LocalCustomColorsPalette.current.buttonText
                )
            }
        }
    )
}

@PreviewLightDark
@Composable
fun ErrorDialogPreview() {
    WeatherAppTheme {
        Surface {
            ErrorDialog(
                onDialogClose = {},
                appError = AppError.NoInternetConnection
            )
        }
    }
}