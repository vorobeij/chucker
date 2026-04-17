package com.chuckerteam.chucker.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.chuckerteam.chucker.navigation.core.ScreenDeeplinkImpl
import com.chuckerteam.chucker.navigation.tabs.MetricsScreenDeeplink
import com.chuckerteam.chucker.navigation.tabs.NetworkingScreenDeeplink
import com.chuckerteam.chucker.navigation.tabs.SettingsScreenDeeplink

private val tabs = listOf(
    NetworkingScreenDeeplink,
    MetricsScreenDeeplink,
    SettingsScreenDeeplink
)
private val fullscreenScreens: List<ScreenDeeplinkImpl> = emptyList()

@Composable
internal fun BottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val showBottomBar = fullscreenScreens.none { currentDestination?.route?.substringBefore("?") == it.route }

    if (showBottomBar) {
        NavigationBar(
            modifier = Modifier,
        ) {
            tabs.forEach { screen ->
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                    icon = { Icon(painter = painterResource(screen.iconRes!!), contentDescription = null) },
                    label = { Text(stringResource(screen.titleRes!!)) },
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(screen.route) {
                                inclusive = false
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}
