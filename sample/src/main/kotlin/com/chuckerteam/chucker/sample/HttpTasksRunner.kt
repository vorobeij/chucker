package com.chuckerteam.chucker.sample

import com.chuckerteam.chucker.sample.tasks.DictionaryHttpTask
import com.chuckerteam.chucker.sample.tasks.DummyImageHttpTask
import com.chuckerteam.chucker.sample.tasks.HttpBinHttpTask
import com.chuckerteam.chucker.sample.tasks.PortfolioOptimizerHttpTask
import com.chuckerteam.chucker.sample.tasks.PostmanEchoHttpTask
import okhttp3.OkHttpClient

class HttpTasksRunner(
    val client: OkHttpClient
) {

    private val httpTasks by lazy {
        listOf(
            HttpBinHttpTask(client),
            DummyImageHttpTask(client),
            PostmanEchoHttpTask(client),
            DictionaryHttpTask(client),
            PortfolioOptimizerHttpTask(client)
        )
    }

    fun run() {
        httpTasks.forEach { it.run() }
    }
}
