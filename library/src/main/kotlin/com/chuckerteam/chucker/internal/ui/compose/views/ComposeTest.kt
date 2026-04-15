package com.chuckerteam.chucker.internal.ui.compose.views

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.chuckerteam.chucker.internal.ui.compose.theme.AppTheme

@Composable
internal fun ComposeTest() {
    AppTheme {
        Text("✅ Compose works!")
    }
}

@Preview(showBackground = true)
@Composable
private fun ComposeTestPreview() {
    ComposeTest()
}
