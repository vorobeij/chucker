package com.chuckerteam.chucker.internal.data.repository.search

// todo search words - split query by space
internal data class SearchFilter(
    val query: String = "", // todo list of words?
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
