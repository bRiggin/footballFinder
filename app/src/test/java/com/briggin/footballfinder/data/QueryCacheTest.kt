package com.briggin.footballfinder.data

import com.briggin.footballfinder.domain.ResultError
import org.junit.Assert.assertEquals
import org.junit.Test

class QueryCacheTest {

    @Test
    fun `WHEN query set THEN expect value returned when queried`() {
        val testQuery = "test cached query"
        with(QueryCache()) {
            query = testQuery
            assertEquals(testQuery, query)
        }
    }

    @Test
    fun `WHEN query set THEN expect players index to increment by 10 when requests`() {
        with(QueryCache()) {
            query = "test cached query"
            assertEquals(0, nextPlayersIndex())
            assertEquals(10, nextPlayersIndex())
            assertEquals(20, nextPlayersIndex())
        }
    }

    @Test
    fun `WHEN query set THEN expect players index to be set`() {
        with(QueryCache()) {
            query = "test cached query"
            assertEquals(0, nextPlayersIndex())
            assertEquals(10, nextPlayersIndex())
            assertEquals(20, nextPlayersIndex())
            query = "second test cached query"
            assertEquals(0, nextPlayersIndex())
            assertEquals(10, nextPlayersIndex())
            assertEquals(20, nextPlayersIndex())
        }
    }

    @Test
    fun `WHEN query set THEN expect teams index to increment by 10 when requests`() {
        with(QueryCache()) {
            query = "test cached query"
            assertEquals(0, nextPlayersIndex())
            assertEquals(10, nextPlayersIndex())
            assertEquals(20, nextPlayersIndex())
        }
    }

    @Test
    fun `WHEN query set THEN expect teams index to be set`() {
        with(QueryCache()) {
            query = "test cached query"
            assertEquals(0, nextTeamsIndex())
            assertEquals(10, nextTeamsIndex())
            assertEquals(20, nextTeamsIndex())
            query = "second test cached query"
            assertEquals(0, nextTeamsIndex())
            assertEquals(10, nextTeamsIndex())
            assertEquals(20, nextTeamsIndex())
        }
    }

    @Test
    fun `WHEN query error set THEN expect value returned when queried`() {
        with(QueryCache()) {
            cacheError(ResultError.Players)
            cacheError(ResultError.Teams)
            assertEquals(setOf(ResultError.Players, ResultError.Teams), getErrors())
        }
    }

    @Test
    fun `WHEN query error requested THEN expect error reset`() {
        with(QueryCache()) {
            cacheError(ResultError.Players)
            cacheError(ResultError.Teams)
            assertEquals(setOf(ResultError.Players, ResultError.Teams), getErrors())
            assertEquals(emptySet<ResultError>(), getErrors())
        }
    }
}