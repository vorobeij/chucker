package com.chuckerteam.chucker.navigation.core

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NamedNavArgument

internal open class TabScreenDeeplink(
    override val path: String,
    @StringRes val titleRes: Int? = null,
    @DrawableRes val iconRes: Int? = null
) : ScreenDeeplinkImpl(path) {

    override val arguments: List<NamedNavArgument> = emptyList()
}
