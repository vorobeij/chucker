package com.chuckerteam.chucker.sample.tasks

import com.chuckerteam.chucker.sample.HttpTask
import com.chuckerteam.chucker.sample.LARGE_JSON
import com.chuckerteam.chucker.sample.Pokemon
import com.chuckerteam.chucker.sample.ReadBytesCallback
import com.chuckerteam.chucker.sample.SEGMENT_SIZE
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class PostmanEchoHttpTask(
    private val client: OkHttpClient,
) : HttpTask {
    override fun run() {
        postResponsePartially()
        postProto()
    }

    private fun postResponsePartially() {
        val body = LARGE_JSON.toRequestBody("application/json".toMediaType())
        val request =
            Request.Builder()
                .url("https://postman-echo.com/post")
                .post(body)
                .build()
        client.newCall(request).enqueue(ReadBytesCallback(SEGMENT_SIZE))
    }

    private fun postProto() {
        val pokemon = Pokemon("Pikachu", level = 99)
        val body = pokemon.encodeByteString().toRequestBody("application/protobuf".toMediaType())
        val request =
            Request.Builder()
                .url("https://postman-echo.com/post")
                .post(body)
                .build()
        client.newCall(request).enqueue(ReadBytesCallback())
    }
}
