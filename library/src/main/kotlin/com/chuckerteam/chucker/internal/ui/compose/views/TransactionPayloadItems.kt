package com.chuckerteam.chucker.internal.ui.compose.views

import android.graphics.Bitmap
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import com.chuckerteam.design.system.theme.AppTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.chuckerteam.chucker.R
import com.chuckerteam.design.system.theme.AppPreview

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

// todo do not use bitmap!!!
@Composable
internal fun ImageItem(image: Bitmap, luminance: Double?, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.chucker_doub_grid)),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            shadowElevation = 4.dp
        ) {
            Image(
                bitmap = image.asImageBitmap(),
                contentDescription = stringResource(id = R.string.chucker_binary_data),
                modifier = Modifier.clip(RoundedCornerShape(8.dp))
            )
        }
    }
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
