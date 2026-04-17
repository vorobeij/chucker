package com.chuckerteam.chucker.navigation.core

import androidx.navigation.NavHostController
import java.lang.ref.WeakReference

internal object NavControllerHolder {

    var navController: WeakReference<NavHostController>? = null

    fun controller() = navController?.get()
}
