package com.chuckerteam.chucker.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.chuckerteam.chucker.navigation.graph.NavGraphs
import com.chuckerteam.chucker.navigation.graph.graphHome
import com.chuckerteam.chucker.navigation.tabs.NetworkingScreenDeeplink

@Composable
internal fun RootTabsScreen(
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        bottomBar = {
            BottomBar(navController)
        }
    ) { innerPadding ->
        NavHost(
            route = NavGraphs.HOME,
            navController = navController,
            startDestination = NetworkingScreenDeeplink.route,
            modifier = Modifier.Companion.padding(innerPadding)
        ) {
            graphHome(navController)
        }
    }
}
