package com.chuckerteam.chucker.internal.ui.compose.screens.main

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
internal fun ChuckerAlertDialog(
    config: DialogConfig?,
    onDismiss: () -> Unit
) {
    if (config == null) return

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = config.data.title) },
        text = { Text(text = config.data.message) },
        confirmButton = {
            TextButton(onClick = {
                config.onPositiveClick()
                onDismiss()
            }) {
                Text(text = config.data.positiveButtonText)
            }
        },
        dismissButton = config.onNegativeClick?.let { onNeg ->
            {
                TextButton(onClick = {
                    onNeg()
                    onDismiss()
                }) {
                    Text(text = config.data.negativeButtonText)
                }
            }
        }
    )
}
