package com.chuckerteam.chucker.internal.ui.compose.screens.transaction

import android.graphics.Bitmap
import android.text.SpannableStringBuilder
import android.text.Spanned

internal sealed class TransactionPayloadItem {
    internal class HeaderItem(val headers: Spanned) : TransactionPayloadItem()

    internal class BodyLineItem(var line: SpannableStringBuilder) : TransactionPayloadItem()

    internal class ImageItem(val image: Bitmap, val luminance: Double?) : TransactionPayloadItem()
}
