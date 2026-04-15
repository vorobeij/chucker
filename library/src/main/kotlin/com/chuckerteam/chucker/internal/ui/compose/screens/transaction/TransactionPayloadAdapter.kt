package com.chuckerteam.chucker.internal.ui.compose.screens.transaction

import android.text.SpannableStringBuilder
import android.text.Spanned

internal sealed class TransactionPayloadItem {
    internal class HeaderItem(val headers: Spanned) : TransactionPayloadItem()

    internal class BodyLineItem(var line: SpannableStringBuilder) : TransactionPayloadItem()
}
