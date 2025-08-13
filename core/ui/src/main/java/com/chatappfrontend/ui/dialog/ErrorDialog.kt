package com.chatappfrontend.ui.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ErrorDialog(
    modifier: Modifier = Modifier,
    title: String = "",
    body: String = "",
    dismissDialog: () -> Unit
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            dismissDialog()
        },
        title = { Text(text = title) },
        text = { Text(text = body) },
        confirmButton = {
            Button(
                onClick = {
                    dismissDialog()
                }
            ) {
                Text(
                    text = "OK",
                )
            }
        }
    )
}