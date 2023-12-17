package app.capstone.rasaku.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import app.capstone.rasaku.ui.theme.RasakuTheme

@Composable
fun ModalDialog(
    body: String,
    setShowDialog: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    properties: DialogProperties = DialogProperties(
        dismissOnClickOutside = true,
        dismissOnBackPress = true
    ),
) {
    Dialog(
        onDismissRequest = { setShowDialog(false) },
        properties = properties
    ) {
        Box(
            modifier = modifier
                .safeContentPadding()
        ) {
            ElevatedCard {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text = body,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}

@Composable
fun ModalDialog(
    title: String,
    body: String,
    positive: String,
    setShowDialog: (Boolean) -> Unit,
    onPositiveClick: () -> Unit,
    modifier: Modifier = Modifier,
    properties: DialogProperties = DialogProperties(
        dismissOnClickOutside = true,
        dismissOnBackPress = true
    ),
) {
    Dialog(
        onDismissRequest = { setShowDialog(false) },
        properties = properties
    ) {
        Box(
            modifier = modifier.safeContentPadding()
        ) {
            ElevatedCard {
                Column(modifier = modifier.padding(16.dp)) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = modifier.height(8.dp))
                    Text(
                        text = body,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = modifier.height(24.dp))
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = modifier.fillMaxWidth()
                    ) {
                        TextButton(onClick = { onPositiveClick() }) {
                            Text(
                                text = positive
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ModalDialog(
    title: String,
    body: String,
    positive: String,
    negative: String,
    setShowDialog: (Boolean) -> Unit,
    onPositiveClick: () -> Unit,
    onNegativeClick: () -> Unit,
    modifier: Modifier = Modifier,
    properties: DialogProperties = DialogProperties(
        dismissOnClickOutside = true,
        dismissOnBackPress = true
    ),
) {
    Dialog(
        onDismissRequest = { setShowDialog(false) },
        properties = properties
    ) {
        Box(
            modifier = modifier.safeContentPadding()
        ) {
            ElevatedCard {
                Column(modifier = modifier.padding(16.dp)) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = modifier.height(8.dp))
                    Text(
                        text = body,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = modifier.height(24.dp))
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = modifier.fillMaxWidth()
                    ) {
                        TextButton(onClick = { onNegativeClick() }) {
                            Text(
                                text = negative
                            )
                        }
                        Spacer(modifier = modifier.width(16.dp))
                        Button(onClick = { onPositiveClick() }) {
                            Text(
                                text = positive
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ModalDialog(
    body: @Composable () -> Unit,
    setShowDialog: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    properties: DialogProperties = DialogProperties(
        dismissOnClickOutside = true,
        dismissOnBackPress = true
    ),
) {
    Dialog(
        onDismissRequest = { setShowDialog(false) },
        properties = properties
    ) {
        Box(
            modifier = modifier
                .safeContentPadding()
        ) {
            ElevatedCard {
                body()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ModalDialogPreview() {
    RasakuTheme {
        ModalDialog(
            setShowDialog = {},
            body = "Soto Lamongan berhasil ditambahkan ke dalam koleksi Makanan Favorite.",
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ModalDialogWithPositiveActionPreview() {
    RasakuTheme {
        ModalDialog(
            setShowDialog = {},
            title = "Berhasil",
            body = "Akun Anda berhasil didaftarkan",
            positive = "OK",
            onPositiveClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ModalDialogWithPositiveAndNegativeActionPreview() {
    RasakuTheme {
        ModalDialog(
            setShowDialog = {},
            title = "Hapus Koleksi Makanan",
            body = "Apakah Anda yakin ingin menghapus koleksi ini?",
            positive = "Hapus",
            onPositiveClick = {},
            negative = "Batalkan",
            onNegativeClick = {}
        )
    }
}
