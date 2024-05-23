package com.synexo.weatherapp.ui.shared.components


import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.synexo.weatherapp.ui.theme.LocalCustomColorsPalette
import com.synexo.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun AcceptRejectDialog(
    @StringRes title: Int,
    @StringRes message: Int,
    @StringRes acceptButtonText: Int,
    @StringRes rejectButtonText: Int,
    onAccept: () -> Unit,
    onReject: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    AcceptRejectDialog(
        title = stringResource(title),
        message = stringResource(message),
        acceptButtonText = stringResource(acceptButtonText),
        rejectButtonText = stringResource(rejectButtonText),
        onAccept = onAccept,
        onReject = onReject,
        onDismissRequest = onDismissRequest,
    )
}

@Composable
fun AcceptRejectDialog(
    title: String,
    message: String,
    acceptButtonText: String,
    rejectButtonText: String,
    onAccept: () -> Unit,
    onReject: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card {
            Column(
                modifier = Modifier
                    .background(LocalCustomColorsPalette.current.cardBackground)
                    .padding(20.dp)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = title,
                    color = LocalCustomColorsPalette.current.text
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = message,
                    textAlign = TextAlign.Center,
                    color = LocalCustomColorsPalette.current.text
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        modifier = Modifier
                            .border(
                                1.dp,
                                LocalCustomColorsPalette.current.buttonBackground,
                                RoundedCornerShape(8.dp)
                            )
                            .height(42.dp)
                            .weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LocalCustomColorsPalette.current.cardBackground
                        ),
                        shape = RoundedCornerShape(8.dp),
                        onClick = onReject
                    ) {
                        Text(
                            text = rejectButtonText,
                            color = LocalCustomColorsPalette.current.textClickable
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(
                        modifier = Modifier
                            .weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LocalCustomColorsPalette.current.textClickable
                        ),
                        shape = RoundedCornerShape(8.dp),
                        onClick = onAccept
                    ) {
                        Text(
                            text = acceptButtonText,
                            color = LocalCustomColorsPalette.current.buttonText
                        )
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun AcceptRejectDialogPreview() {
    WeatherAppTheme {
        Surface {
            AcceptRejectDialog(
                title = "Title",
                message = "Message",
                acceptButtonText = "Accept",
                rejectButtonText = "Reject",
                onAccept = {},
                onReject = {},
                onDismissRequest = {},
            )
        }
    }
}