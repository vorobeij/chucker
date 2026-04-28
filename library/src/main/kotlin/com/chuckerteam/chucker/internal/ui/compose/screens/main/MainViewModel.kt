package com.chuckerteam.chucker.internal.ui.compose.screens.main

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.chuckerteam.chucker.internal.data.entity.HttpTransaction
import com.chuckerteam.chucker.internal.data.entity.HttpTransactionTuple
import com.chuckerteam.chucker.internal.data.repository.Di
import com.chuckerteam.chucker.internal.data.repository.search.SearchFilter
import com.chuckerteam.chucker.internal.support.NotificationHelper
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

internal class MainViewModel : ViewModel() {

    private val currentFilter = MutableLiveData(SearchFilter())

    val transactions: LiveData<List<HttpTransactionTuple>> =
        currentFilter.switchMap { searchFilter ->
            with(Di.transactionRepository) {
                getFilteredTransactionTuples(searchFilter)
            }
        }

    private val _permissionRequest = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val permissionRequest = _permissionRequest.asSharedFlow()

    suspend fun getAllTransactions(): List<HttpTransaction> =
        Di.transactionRepository.getAllTransactions()

    fun updateItemsFilter(searchFilter: SearchFilter) {
        currentFilter.value = searchFilter
    }

    fun clearTransactions() {
        viewModelScope.launch {
            Di.transactionRepository.deleteAllTransactions()
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
