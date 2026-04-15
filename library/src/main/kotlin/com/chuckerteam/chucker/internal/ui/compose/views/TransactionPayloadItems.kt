package com.chuckerteam.chucker.internal.ui.compose.views

import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.chuckerteam.chucker.R
import com.chuckerteam.design.system.theme.AppPreview
import com.chuckerteam.design.system.theme.AppTheme

@Composable
internal fun HeaderItem(headers: Spanned, modifier: Modifier = Modifier) {
    Text(
        text = headers.toString(),
        style = AppTheme.typography.bodyMedium,
        color = AppTheme.colorScheme.onBackground,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.chucker_doub_grid)),
    )
}

@Composable
internal fun BodyLineItem(line: SpannableStringBuilder, modifier: Modifier = Modifier) {
    Text(
        text = line.toString(),
        fontFamily = FontFamily.Monospace,
        style = AppTheme.typography.bodySmall,
        color = AppTheme.colorScheme.onBackground,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.chucker_doub_grid))
    )
}

@AppPreview
@Composable
private fun PayloadItemsPreview() {
    AppTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            HeaderItem(headers = SpannableString("Content-Type: application/json"))
            BodyLineItem(line = SpannableStringBuilder("{\"status\": \"ok\"}"))
        }
    }
}
