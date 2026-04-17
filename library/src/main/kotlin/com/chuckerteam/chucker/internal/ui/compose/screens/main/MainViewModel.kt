package com.chuckerteam.chucker.internal.ui.compose.screens.main

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.text.TextUtils
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.chuckerteam.chucker.internal.data.entity.HttpTransaction
import com.chuckerteam.chucker.internal.data.entity.HttpTransactionTuple
import com.chuckerteam.chucker.internal.data.repository.RepositoryProvider
import com.chuckerteam.chucker.internal.support.NotificationHelper
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

internal class MainViewModel : ViewModel() {

    private val currentFilter = MutableLiveData("")

    val transactions: LiveData<List<HttpTransactionTuple>> =
        currentFilter.switchMap { searchQuery ->
            with(RepositoryProvider.transaction()) {
                when {
                    searchQuery.isNullOrBlank() -> getSortedTransactionTuples()
                    TextUtils.isDigitsOnly(searchQuery) -> getFilteredTransactionTuples(searchQuery, "")
                    else -> getFilteredTransactionTuples("", searchQuery)
                }
            }
        }

    private val _permissionRequest = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val permissionRequest = _permissionRequest.asSharedFlow()

    suspend fun getAllTransactions(): List<HttpTransaction> =
        RepositoryProvider.transaction().getAllTransactions()

    fun updateItemsFilter(searchQuery: String) {
        currentFilter.value = searchQuery
    }

    fun clearTransactions() {
        viewModelScope.launch {
            RepositoryProvider.transaction().deleteAllTransactions()
        }
        NotificationHelper.clearBuffer() // Fixed: removed erroneous .Companion
    }

    /**
     * Check and request notification permission (API 33+ only)
     * Emits to permissionRequest flow if permission should be requested
     */
    fun checkNotificationPermission(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return

        val hasPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED

        if (!hasPermission) {
            viewModelScope.launch {
                _permissionRequest.emit(Unit)
            }
        }
    }
}
