package com.chuckerteam.chucker.internal.ui.compose.screens.main

import com.chuckerteam.chucker.internal.data.model.DialogData

internal data class DialogConfig(
    val data: DialogData,
    val onPositiveClick: () -> Unit,
    val onNegativeClick: (() -> Unit)? = null
)
