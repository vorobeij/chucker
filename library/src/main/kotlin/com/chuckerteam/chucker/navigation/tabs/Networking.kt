package com.chuckerteam.chucker.navigation.tabs

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.chuckerteam.chucker.R
import com.chuckerteam.chucker.internal.ui.compose.screens.main.ChuckerScreenRoot
import com.chuckerteam.chucker.navigation.core.TabScreenDeeplink
import com.chuckerteam.chucker.navigation.core.composableNoAnim

internal object NetworkingScreenDeeplink : TabScreenDeeplink(
    path = "networking",
    iconRes = R.drawable.chucker_ic_network,
    titleRes = R.string.chucker_tab_networking
)

internal fun NavGraphBuilder.networkingScreenComposable(
    navController: NavHostController
) = composableNoAnim(
    route = NetworkingScreenDeeplink.route,
) {
    val context = LocalContext.current
    ChuckerScreenRoot(
        applicationName = "Chucker",
        onTransactionClick = {
            // todo open composable
        }
    )
}

@Composable
internal fun ScreenNetworking() {
    Text("networking")
}
