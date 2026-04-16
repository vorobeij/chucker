package com.chuckerteam.chucker.sample.tasks

import com.chuckerteam.chucker.sample.HttpTask
import com.chuckerteam.chucker.sample.ReadBytesCallback
import com.chuckerteam.chucker.sample.SEGMENT_SIZE
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class PortfolioOptimizerHttpTask(
    private val client: OkHttpClient,
) : HttpTask {

    override fun run() {
        post()
    }

    private fun post() {
        val body = """
         {
          "assets": 2,
          "assetsCovarianceMatrix": [
            [
              0.0025,
              0.0005
            ],
            [
              0.0005,
              0.0100
            ]
          ]
        }
        """.trimIndent().toRequestBody("application/json".toMediaType())
        val request =
            Request.Builder()
                .url("https://api.portfoliooptimizer.io/v1/portfolio/optimization/minimum-variance")
                .post(body)
                .build()
        client.newCall(request).enqueue(ReadBytesCallback(SEGMENT_SIZE))
    }
}
