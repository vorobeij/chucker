package com.chuckerteam.chucker.sample.tasks

import com.chuckerteam.chucker.sample.HttpTask
import com.chuckerteam.chucker.sample.ReadBytesCallback
import com.chuckerteam.chucker.sample.SEGMENT_SIZE
import okhttp3.OkHttpClient
import okhttp3.Request

internal class DictionaryHttpTask(
    private val client: OkHttpClient,
) : HttpTask {

    override fun run() {
        listOf(
            "hello",
            "bridge",
            "client",
            "shelve"
        ).forEach(::getWord)
    }

    private fun getWord(word: String) {
        val request =
            Request.Builder()
                .url("https://api.dictionaryapi.dev/api/v2/entries/en/$word")
                .get()
                .build()
        client.newCall(request).enqueue(ReadBytesCallback(SEGMENT_SIZE))
    }
}
