package com.chuckerteam.chucker.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.chuckerteam.chucker.navigation.tabs.metricsComposable
import com.chuckerteam.chucker.navigation.tabs.networkingScreenComposable
import com.chuckerteam.chucker.navigation.tabs.settingsComposable

internal fun NavGraphBuilder.graphHome(
    navController: NavHostController
): NavGraphBuilder {

    metricsComposable(navController)
    networkingScreenComposable(navController)
    settingsComposable(navController)

    return this
}
