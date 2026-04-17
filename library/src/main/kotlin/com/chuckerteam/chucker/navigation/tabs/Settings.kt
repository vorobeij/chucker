package com.chuckerteam.chucker.navigation.tabs

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.chuckerteam.chucker.R
import com.chuckerteam.chucker.navigation.core.TabScreenDeeplink
import com.chuckerteam.chucker.navigation.core.composableNoAnim

internal object SettingsScreenDeeplink : TabScreenDeeplink(
    path = "settings",
    iconRes = R.drawable.chucker_ic_settings,
    titleRes = R.string.chucker_tab_settings
)

internal fun NavGraphBuilder.settingsComposable(navController: NavHostController) = composableNoAnim(SettingsScreenDeeplink.route) {
    ScreenSettings()
}

@Composable
internal fun ScreenSettings() {
    Text("settings")
}
