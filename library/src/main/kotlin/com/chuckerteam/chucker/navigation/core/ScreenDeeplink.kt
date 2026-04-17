package com.chuckerteam.chucker.navigation.core

import androidx.navigation.NamedNavArgument

/**
 * Data for compose navigation
 */
internal interface ScreenDeeplink {

    val path: String
    val arguments: List<NamedNavArgument>
    val route: String
}
