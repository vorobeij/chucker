package com.chuckerteam.chucker.internal.data.repository

import androidx.lifecycle.LiveData
import com.chuckerteam.chucker.internal.data.entity.HttpTransaction
import com.chuckerteam.chucker.internal.data.entity.HttpTransactionTuple
import com.chuckerteam.chucker.internal.data.repository.search.SearchFilter

/**
 * Repository Interface representing all the operations that are needed to let Chucker work
 * with [HttpTransaction] and [HttpTransactionTuple]. Please use [HttpTransactionDatabaseRepository] that
 * uses Room and SqLite to run those operations.
 */
internal interface HttpTransactionRepository {
    suspend fun insertTransaction(transaction: HttpTransaction)

    suspend fun updateTransaction(transaction: HttpTransaction): Int

    suspend fun deleteOldTransactions(threshold: Long)

    suspend fun deleteAllTransactions()

    fun getSortedTransactionTuples(): LiveData<List<HttpTransactionTuple>>

    fun getFilteredTransactionTuples(
        searchFilter: SearchFilter,
    ): LiveData<List<HttpTransactionTuple>>

    fun getTransaction(transactionId: Long): LiveData<HttpTransaction?>

    suspend fun getAllTransactions(): List<HttpTransaction>

    fun getTransactionsInTimeRange(minTimestamp: Long?): List<HttpTransaction>
}
