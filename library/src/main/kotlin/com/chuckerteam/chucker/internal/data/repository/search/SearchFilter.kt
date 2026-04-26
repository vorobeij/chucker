package com.chuckerteam.chucker.internal.data.repository.search

internal data class SearchFilter(
    val query: String = "",
    val method: HttpMethod? = null,
    val responseCode: Int? = null,
    val searchIn: Set<SearchIn> = emptySet()
)

internal enum class SearchIn {
    PATH,
    REQUEST_BODY,
    RESPONSE_BODY,
}

internal enum class HttpMethod {
    GET,
    POST,
}
