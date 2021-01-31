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
            assertEquals(0, playersIndexAndIncrement())
            assertEquals(10, playersIndexAndIncrement())
            assertEquals(20, playersIndexAndIncrement())
        }
    }

    @Test
    fun `WHEN query set THEN expect players index to be reset`() {
        with(QueryCache()) {
            query = "test cached query"
            assertEquals(0, playersIndexAndIncrement())
            assertEquals(10, playersIndexAndIncrement())
            assertEquals(20, playersIndexAndIncrement())
            query = "second test cached query"
            assertEquals(0, playersIndexAndIncrement())
            assertEquals(10, playersIndexAndIncrement())
            assertEquals(20, playersIndexAndIncrement())
        }
    }

    @Test
    fun `WHEN current player index request THEN expect it to have no effect on value`() {
        with(QueryCache()) {
            query = "test cached query"
            assertEquals(0, playersIndexAndIncrement())
            assertEquals(10, playersIndexAndIncrement())
            assertEquals(20, currentPlayersIndex())
            assertEquals(20, currentPlayersIndex())
            assertEquals(20, currentPlayersIndex())
        }
    }

    @Test
    fun `WHEN query set THEN expect teams index to increment by 10 when requests`() {
        with(QueryCache()) {
            query = "test cached query"
            assertEquals(0, playersIndexAndIncrement())
            assertEquals(10, playersIndexAndIncrement())
            assertEquals(20, playersIndexAndIncrement())
        }
    }

    @Test
    fun `WHEN query set THEN expect teams index to be reset`() {
        with(QueryCache()) {
            query = "test cached query"
            assertEquals(0, teamsIndexAndIncrement())
            assertEquals(10, teamsIndexAndIncrement())
            assertEquals(20, teamsIndexAndIncrement())
            query = "second test cached query"
            assertEquals(0, teamsIndexAndIncrement())
            assertEquals(10, teamsIndexAndIncrement())
            assertEquals(20, teamsIndexAndIncrement())
        }
    }

    @Test
    fun `WHEN current team index request THEN expect it to have no effect on value`() {
        with(QueryCache()) {
            query = "test cached query"
            assertEquals(0, teamsIndexAndIncrement())
            assertEquals(10, teamsIndexAndIncrement())
            assertEquals(20, currentTeamsIndex())
            assertEquals(20, currentTeamsIndex())
            assertEquals(20, currentTeamsIndex())
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