package com.synexo.weatherapp.core.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.synexo.weatherapp.core.ui.LocalCustomColorsPalette
import com.synexo.weatherapp.core.ui.LocationPermissionTextProvider
import com.synexo.weatherapp.core.ui.PermissionTextProvider
import com.synexo.weatherapp.core.ui.R
import com.synexo.weatherapp.core.ui.WeatherAppTheme


@Composable
fun PermissionDialog(
    permissionTextProvider: PermissionTextProvider,
    isPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
    onGoToAppSettingsClick: () -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
        Card {
            Column(
                modifier = Modifier
                    .background(LocalCustomColorsPalette.current.cardBackground)
                    .padding(20.dp)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = stringResource(R.string.permission_dialog_title),
                    color = LocalCustomColorsPalette.current.text
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = stringResource(
                        id = permissionTextProvider.getDescription(
                            isPermanentlyDeclined
                        )
                    ),
                    textAlign = TextAlign.Center,
                    color = LocalCustomColorsPalette.current.text
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LocalCustomColorsPalette.current.textClickable
                    ),
                    shape = RoundedCornerShape(8.dp),
                    onClick = if (isPermanentlyDeclined) {
                        onGoToAppSettingsClick
                    } else {
                        onOkClick
                    }
                ) {
                    Text(
                        text = if (isPermanentlyDeclined) {
                            stringResource(R.string.permission_dialog_go_to_settings)
                        } else {
                            stringResource(R.string.permission_dialog_grant_permission)
                        },
                        color = LocalCustomColorsPalette.current.buttonText
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun PermissionDialogPreview(
    @PreviewParameter(UserPreviewParameterProvider::class) isPermanentlyDeclined: Boolean
) {
    WeatherAppTheme {
        Surface {
            PermissionDialog(
                permissionTextProvider = LocationPermissionTextProvider(),
                isPermanentlyDeclined = isPermanentlyDeclined,
                onDismiss = {},
                onOkClick = {},
                onGoToAppSettingsClick = {}
            )
        }
    }
}

class UserPreviewParameterProvider : PreviewParameterProvider<Boolean> {
    override val values = sequenceOf(true, false)
}

