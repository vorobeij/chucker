package com.chuckerteam.chucker.internal.data.repository.search

import androidx.sqlite.db.SimpleSQLiteQuery

internal class SearchQueryBuilder {

    private val textFields = listOf(
        "protocol",
        "method",
        "url",
        "host",
        "path",
        "scheme",
        "responseTlsVersion",
        "responseCipherSuite",
        "requestContentType",
        "requestHeaders",
        "requestBody",
        "responseMessage",
        "error",
        "responseContentType",
        "responseHeaders",
        "responseBody",
        "graphQlOperationName",
    )

    private val selectFields = listOf(
        "id",
        "requestDate",
        "tookMs",
        "protocol",
        "method",
        "host",
        "path",
        "scheme",
        "responseCode",
        "requestPayloadSize",
        "responsePayloadSize",
        "error",
        "graphQLDetected",
        "graphQlOperationName",
    )

    fun build(searchFilter: SearchFilter): SimpleSQLiteQuery {
        val selectedFields = selectFields.joinToString(", ")

        val query = buildString {
            appendln("SELECT $selectedFields")
            appendln("FROM transactions")

            val conditions = buildList {
                responseCode(searchFilter)
                method(searchFilter)
                queryFields(searchFilter)
            }

            when {
                conditions.isNotEmpty() -> {
                    append("WHERE ")
                    appendln(conditions.joinToString(" AND "))
                }

                else -> Unit
            }
            appendln("")
            appendln("ORDER BY requestDate DESC")
        }
        return SimpleSQLiteQuery(query, null)
    }

    private fun MutableList<String>.queryFields(searchFilter: SearchFilter) {
        val queryFields = when {
            searchFilter.searchIn.isEmpty() && searchFilter.query.isNotEmpty() -> textFields
            searchFilter.query.isNotEmpty() -> searchFilter.searchIn.map {
                when (it) {
                    SearchIn.PATH -> "path"
                    SearchIn.REQUEST_BODY -> "requestBody"
                    SearchIn.RESPONSE_BODY -> "responseBody"
                }
            }

            else -> emptyList()
        }
        if (queryFields.isNotEmpty()) {
            add("(" + queryFields.joinToString(" OR ") { "$it LIKE '%${searchFilter.query}%'" } + ")")
        }
    }

    private fun MutableList<String>.method(searchFilter: SearchFilter) {
        searchFilter.method?.let { method ->
            add("method='${method.name.uppercase()}'")
        }
    }

    private fun MutableList<String>.responseCode(searchFilter: SearchFilter) {
        searchFilter.responseCode?.let { responseCode ->
            add("responseCode='$responseCode'")
        }
    }

    private fun StringBuilder.appendln(s: String, lines: Int = 1) {
        append(s)
        repeat((1..lines).count()) { append("\n") }
    }
}
