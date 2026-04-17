package com.chuckerteam.chucker.navigation.tabs

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.chuckerteam.chucker.R
import com.chuckerteam.chucker.navigation.core.TabScreenDeeplink
import com.chuckerteam.chucker.navigation.core.composableNoAnim

internal object MetricsScreenDeeplink : TabScreenDeeplink(
    path = "metrics",
    iconRes = R.drawable.chucker_ic_user_search,
    titleRes = R.string.chucker_tab_metrics
)

internal fun NavGraphBuilder.metricsComposable(
    navController: NavHostController
) = composableNoAnim(MetricsScreenDeeplink.route) {
    ScreenMetrics(
    )
}

@Composable
internal fun ScreenMetrics() {
    Text("metrics")
}
