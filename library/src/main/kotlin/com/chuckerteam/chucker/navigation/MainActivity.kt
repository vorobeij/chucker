package com.chuckerteam.chucker.navigation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.chuckerteam.chucker.internal.core.BaseChuckerActivityCompose
import com.chuckerteam.chucker.navigation.core.NavControllerHolder
import com.chuckerteam.chucker.navigation.graph.RootNavigationGraph
import com.chuckerteam.design.system.theme.AppTheme
import java.lang.ref.WeakReference

internal class MainActivity : BaseChuckerActivityCompose() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                val controller = rememberNavController()
                NavControllerHolder.navController = WeakReference(controller) // todo check lifecycle
                RootNavigationGraph(
                    navHostController = controller
                )
            }
        }
    }
}
