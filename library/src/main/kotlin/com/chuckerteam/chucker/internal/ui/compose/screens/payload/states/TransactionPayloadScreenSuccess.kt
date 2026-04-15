package com.chuckerteam.chucker.internal.ui.compose.screens.payload.states

import android.text.SpannableStringBuilder
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.chuckerteam.chucker.R
import com.chuckerteam.chucker.internal.ui.compose.screens.payload.TransactionPayloadItem
import com.chuckerteam.chucker.internal.ui.compose.views.BodyLineItem
import com.chuckerteam.chucker.internal.ui.compose.views.HeaderItem
import com.chuckerteam.design.system.theme.AppPreview
import com.chuckerteam.design.system.theme.AppTheme

@Composable
internal fun TransactionPayloadScreenSuccess(
    showSearchSummary: Boolean,
    searchSummaryText: String,
    items: List<TransactionPayloadItem> // todo persistent list
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        if (showSearchSummary) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colorResource(id = R.color.chucker_color_primary))
                        .padding(horizontal = dimensionResource(id = R.dimen.chucker_doub_grid), vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = searchSummaryText,
                        color = colorResource(id = R.color.chucker_color_on_primary),
                        style = AppTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = { /* scroll up logic */ }) {
                        Image(
                            painter = painterResource(id = R.drawable.chucker_ic_arrow_down),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(colorResource(id = R.color.chucker_color_on_primary)),
                            modifier = Modifier.rotate(180f)
                        )
                    }
                    IconButton(onClick = { /* scroll down logic */ }) {
                        Image(
                            painter = painterResource(id = R.drawable.chucker_ic_arrow_down),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(colorResource(id = R.color.chucker_color_on_primary))
                        )
                    }
                }
            }
        }
        itemsIndexed(items, key = { it, item -> item.hashCode() + it }) { _, item ->
            when (item) {
                is TransactionPayloadItem.HeaderItem -> HeaderItem(item.headers)
                is TransactionPayloadItem.BodyLineItem -> BodyLineItem(item.line)
            }
        }
    }
}

@AppPreview
@Composable
private fun Preview(
    @PreviewParameter(StateSuccessPreviewProvider::class) state: PreviewParams
) {
    AppTheme {
        TransactionPayloadScreenSuccess(
            showSearchSummary = state.showSearchSummary,
            searchSummaryText = state.searchSummaryText,
            items = state.items
        )
    }
}

private data class PreviewParams(
    val showSearchSummary: Boolean,
    val searchSummaryText: String,
    val items: List<TransactionPayloadItem>
)

private class StateSuccessPreviewProvider : PreviewParameterProvider<PreviewParams> {
    override val values: Sequence<PreviewParams> = sequenceOf(
        PreviewParams(
            showSearchSummary = true,
            searchSummaryText = "search summary",
            items = listOf(
                TransactionPayloadItem.BodyLineItem(
                    SpannableStringBuilder.valueOf("(body is empty)")
                )
            )
        )
    )
}
