package com.chuckerteam.chucker.internal.data.repository

import com.chuckerteam.chucker.internal.data.repository.search.HttpMethod
import com.chuckerteam.chucker.internal.data.repository.search.SearchFilter
import com.chuckerteam.chucker.internal.data.repository.search.SearchIn
import com.chuckerteam.chucker.internal.data.repository.search.SearchQueryBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SearchQueryBuilderTest {

    @Test
    fun `query only`() {
        val sql = SearchQueryBuilder().build(
            SearchFilter(
                query = "myquery",
            )
        ).sql

        compare(
            generated = sql,
            expected = """
                select id, requestdate, tookms, protocol, method, host, path, scheme, responsecode, requestpayloadsize, responsepayloadsize, error, graphqldetected, graphqloperationname
                from transactions
                where (protocol like '%myquery%' or method like '%myquery%' or url like '%myquery%' or host like '%myquery%' or path like '%myquery%' or scheme like '%myquery%' or responsetlsversion like '%myquery%' or responseciphersuite like '%myquery%' or requestcontenttype like '%myquery%' or requestheaders like '%myquery%' or requestbody like '%myquery%' or responsemessage like '%myquery%' or error like '%myquery%' or responsecontenttype like '%myquery%' or responseheaders like '%myquery%' or responsebody like '%myquery%' or graphqloperationname like '%myquery%')
                order by requestdate desc
            """.trimIndent()
        )
    }

    @Test
    fun `query and method`() {
        val sql = SearchQueryBuilder().build(
            SearchFilter(
                query = "test",
                method = HttpMethod.POST
            )
        ).sql

        compare(
            generated = sql,
            expected = """
                select id, requestdate, tookms, protocol, method, host, path, scheme, responsecode, requestpayloadsize, responsepayloadsize, error, graphqldetected, graphqloperationname
                from transactions
                where method='post' and (protocol like '%test%' or method like '%test%' or url like '%test%' or host like '%test%' or path like '%test%' or scheme like '%test%' or responsetlsversion like '%test%' or responseciphersuite like '%test%' or requestcontenttype like '%test%' or requestheaders like '%test%' or requestbody like '%test%' or responsemessage like '%test%' or error like '%test%' or responsecontenttype like '%test%' or responseheaders like '%test%' or responsebody like '%test%' or graphqloperationname like '%test%')
                order by requestdate desc
            """.trimIndent()
        )
    }

    @Test
    fun `empty search with method`() {
        val sql = SearchQueryBuilder().build(
            SearchFilter(
                query = "",
                method = HttpMethod.POST
            )
        ).sql

        compare(
            generated = sql,
            expected = """
                select id, requestdate, tookms, protocol, method, host, path, scheme, responsecode, requestpayloadsize, responsepayloadsize, error, graphqldetected, graphqloperationname
                from transactions
                where method='post'
                order by requestdate desc
            """.trimIndent()
        )
    }

    @Test
    fun `search in many fields`() {
        val sql = SearchQueryBuilder().build(
            SearchFilter(
                query = "test",
                method = HttpMethod.POST,
                searchIn = setOf(
                    SearchIn.PATH,
                    SearchIn.RESPONSE_BODY
                )
            )
        ).sql

        compare(
            generated = sql,
            expected = """
                select id, requestdate, tookms, protocol, method, host, path, scheme, responsecode, requestpayloadsize, responsepayloadsize, error, graphqldetected, graphqloperationname
                from transactions
                where method='post' and (path like '%test%' or responsebody like '%test%')
                order by requestdate desc
            """.trimIndent()
        )
    }

    @Test
    fun `empty search`() {
        val sql = SearchQueryBuilder().build(
            SearchFilter()
        ).sql

        compare(
            generated = sql,
            expected = """
                SELECT id, requestDate, tookMs, protocol, method, host, path, scheme, responseCode, requestPayloadSize, responsePayloadSize, error, graphQLDetected, graphQlOperationName
                FROM transactions
                ORDER BY requestDate DESC
            """.trimIndent()
        )
    }

    @Test
    fun `response code search`() {
        val sql = SearchQueryBuilder().build(
            SearchFilter(
                query = "myquery",
                responseCode = 200
            )
        ).sql

        compare(
            generated = sql,
            expected = """
                select id, requestdate, tookms, protocol, method, host, path, scheme, responsecode, requestpayloadsize, responsepayloadsize, error, graphqldetected, graphqloperationname
                from transactions
                where responsecode='200' and (protocol like '%myquery%' or method like '%myquery%' or url like '%myquery%' or host like '%myquery%' or path like '%myquery%' or scheme like '%myquery%' or responsetlsversion like '%myquery%' or responseciphersuite like '%myquery%' or requestcontenttype like '%myquery%' or requestheaders like '%myquery%' or requestbody like '%myquery%' or responsemessage like '%myquery%' or error like '%myquery%' or responsecontenttype like '%myquery%' or responseheaders like '%myquery%' or responsebody like '%myquery%' or graphqloperationname like '%myquery%')
                order by requestdate desc
            """.trimIndent()
        )
    }

    private fun compare(
        generated: String,
        expected: String
    ) {
        assertEquals(
            expected.clean(),
            generated.clean(),
        )
    }

    private fun String.clean() = lowercase().trimIndent().trim().replace("\n", " ").replace("\\s+".toRegex(), " ")

}
