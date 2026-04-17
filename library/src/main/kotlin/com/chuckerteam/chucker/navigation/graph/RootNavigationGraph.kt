package com.chuckerteam.chucker.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.chuckerteam.chucker.navigation.RootTabsScreen
import com.chuckerteam.chucker.navigation.core.composableNoAnim

@Composable
internal fun RootNavigationGraph(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        route = NavGraphs.ROOT,
        startDestination = NavGraphs.HOME
    ) {
        graphHome()
        // other graphs here
    }
}

private fun NavGraphBuilder.graphHome() {
    composableNoAnim(route = NavGraphs.HOME) {
        RootTabsScreen()
    }
}
