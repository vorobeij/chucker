package com.chuckerteam.chucker.internal.ui.compose.views

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
internal fun ComposeTest() {
    MaterialTheme {
        Text("✅ Compose works!")
    }
}

@Preview(showBackground = true)
@Composable
private fun ComposeTestPreview() {
    ComposeTest()
}
