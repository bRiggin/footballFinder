package com.briggin.footballfinder.data

import com.briggin.footballfinder.api.retorfit.dto.ApiError
import com.briggin.footballfinder.api.retorfit.dto.Success
import com.briggin.footballfinder.domain.*
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FootballRepositoryTest {

    private val localStorage: FootballStorage = mockk(relaxed = true)
    private val remoteApi: FootballApi = mockk(relaxed = true)
    private val mapper: DataSourceMapper = mockk(relaxed = true)
    private val cache: QueryCache = mockk(relaxed = true)

    private lateinit var dataSource: FootballDataSource

    @Before
    fun `configure test`() {
        coEvery { localStorage.getPlayers(any(), any()) } returns ApiError()
        coEvery { localStorage.getTeams(any(), any()) } returns ApiError()
        coEvery { remoteApi.getPlayers(any(), any()) } returns ApiError()
        coEvery { remoteApi.getTeams(any(), any()) } returns ApiError()
        every { mapper.mapPlayerResponse(any()) } returns NoDomainsFound()
        every { mapper.mapTeamResponse(any()) } returns NoDomainsFound()

        dataSource = FootballRepository(localStorage, remoteApi, mapper, cache)
    }

    @Test
    fun `WHEN search performed THEN expected query cached`() =
        runBlocking {
            val testQuery = "test football query"
            dataSource.performSearch(testQuery)

            coVerify { cache.query = testQuery }
        }

    @Test
    fun `WHEN search performed THEN expected remote api queried for players and teams with index 0`() =
        runBlocking {
            val testQuery = "test football query"
            every { cache.query } returns testQuery
            dataSource.performSearch(testQuery)

            coVerify { remoteApi.getPlayers(testQuery) }
            coVerify { remoteApi.getTeams(testQuery) }
        }

    @Test
    fun `GIVEN remote api returns players WHEN search performed THEN expected storage to be updated`() =
        runBlocking {
            val testQuery = "test football query"
            val testResponse = listOf<PlayerDomain>(mockk(), mockk(), mockk())
            every { cache.query } returns testQuery
            coEvery { remoteApi.getPlayers(testQuery) } returns Success(testResponse)

            dataSource.performSearch(testQuery)

            coVerify { localStorage.updatePlayers(testResponse) }
        }

    @Test
    fun `GIVEN remote api returns error for players WHEN search performed THEN expected storage not updated`() =
        runBlocking {
            val testQuery = "test football query"
            every { cache.query } returns testQuery
            coEvery { remoteApi.getPlayers(testQuery) } returns ApiError()

            dataSource.performSearch(testQuery)

            coVerify(exactly = 0) { localStorage.updatePlayers(any()) }
        }

    @Test
    fun `GIVEN remote api returns error for players WHEN search performed THEN expected error cached`() =
        runBlocking {
            val testQuery = "test football query"
            every { cache.query } returns testQuery
            coEvery { remoteApi.getPlayers(testQuery) } returns ApiError()

            dataSource.performSearch(testQuery)

            verify { cache.cacheError(ResultError.Players) }
        }

    @Test
    fun `GIVEN remote api returns players WHEN search performed THEN expected storage data returned`() =
        runBlocking {
            val testQuery = "test football query"
            val testResponse = Success(listOf<PlayerDomain>(mockk(), mockk(), mockk()))
            val testDomain = Partial<PlayerDomain>(emptyList())
            every { cache.query } returns testQuery
            coEvery { remoteApi.getPlayers(testQuery) } returns Success(emptyList())
            coEvery { localStorage.getPlayers(testQuery) } returns testResponse
            every { mapper.mapPlayerResponse(testResponse) } returns testDomain

            assertEquals(testDomain, dataSource.performSearch(testQuery).players)
        }

    @Test
    fun `GIVEN remote api returns error for players WHEN search performed THEN expected storage data returned`() =
        runBlocking {
            val testQuery = "test football query"
            val testResponse = Success(listOf<PlayerDomain>(mockk(), mockk(), mockk()))
            val testDomain = Complete<PlayerDomain>(emptyList())
            every { cache.query } returns testQuery
            coEvery { remoteApi.getPlayers(testQuery) } returns ApiError()
            coEvery { localStorage.getPlayers(testQuery) } returns testResponse
            every { mapper.mapPlayerResponse(testResponse) } returns testDomain

            assertEquals(testDomain, dataSource.performSearch(testQuery).players)
        }

    @Test
    fun `GIVEN remote api returns error for players WHEN search performed THEN expected error returned`() =
        runBlocking {
            val testQuery = "test football query"
            val testResponse = Success(listOf<PlayerDomain>(mockk(), mockk(), mockk()))
            val testDomain = Complete<PlayerDomain>(emptyList())
            val testError = setOf(ResultError.Teams, ResultError.Players)
            every { cache.query } returns testQuery
            every { cache.getErrors() } returns testError
            coEvery { remoteApi.getPlayers(testQuery) } returns ApiError()
            coEvery { localStorage.getPlayers(testQuery) } returns testResponse
            every { mapper.mapPlayerResponse(testResponse) } returns testDomain

            assertEquals(testError, dataSource.performSearch(testQuery).remoteErrors)
        }

    @Test
    fun `GIVEN remote api returns teams WHEN search performed THEN expected storage to be updated`() =
        runBlocking {
            val testQuery = "test football query"
            val testResponse = listOf<TeamDomain>(mockk(), mockk(), mockk())
            every { cache.query } returns testQuery
            coEvery { remoteApi.getTeams(testQuery) } returns Success(testResponse)

            dataSource.performSearch(testQuery)

            coVerify { localStorage.updateTeams(testResponse) }
        }

    @Test
    fun `GIVEN remote api returns error for teams WHEN search performed THEN expected storage not updated`() =
        runBlocking {
            val testQuery = "test football query"
            every { cache.query } returns testQuery
            coEvery { remoteApi.getTeams(testQuery) } returns ApiError()

            dataSource.performSearch(testQuery)

            coVerify(exactly = 0) { localStorage.updateTeams(any()) }
        }

    @Test
    fun `GIVEN remote api returns error for teams WHEN search performed THEN expected error cached`() =
        runBlocking {
            val testQuery = "test football query"
            every { cache.query } returns testQuery
            coEvery { remoteApi.getTeams(testQuery) } returns ApiError()

            dataSource.performSearch(testQuery)

            verify { cache.cacheError(ResultError.Teams) }
        }

    @Test
    fun `GIVEN remote api returns teams WHEN search performed THEN expected storage data returned`() =
        runBlocking {
            val testQuery = "test football query"
            val testResponse = Success(listOf<TeamDomain>(mockk(), mockk(), mockk()))
            val testDomain = Partial<TeamDomain>(emptyList())
            every { cache.query } returns testQuery
            coEvery { remoteApi.getTeams(testQuery) } returns Success(emptyList())
            coEvery { localStorage.getTeams(testQuery) } returns testResponse
            every { mapper.mapTeamResponse(testResponse) } returns testDomain

            assertEquals(testDomain, dataSource.performSearch(testQuery).teams)
        }

    @Test
    fun `GIVEN remote api returns error for teams WHEN search performed THEN expected storage data returned`() =
        runBlocking {
            val testQuery = "test football query"
            val testResponse = Success(listOf<TeamDomain>(mockk(), mockk(), mockk()))
            val testDomain = NoDomainsFound<TeamDomain>()
            every { cache.query } returns testQuery
            coEvery { remoteApi.getTeams(testQuery) } returns ApiError()
            coEvery { localStorage.getTeams(testQuery) } returns testResponse
            every { mapper.mapTeamResponse(testResponse) } returns testDomain

            assertEquals(testDomain, dataSource.performSearch(testQuery).teams)
        }

    @Test
    fun `WHEN more players requested THEN expect current query index included in request`() =
        runBlocking {
            val testQuery = "test football query"
            val testIndex = 453L
            every { cache.currentPlayersIndex() } returns testIndex
            every { cache.query } returns testQuery

            dataSource.getMorePlayers()

            coVerify { remoteApi.getPlayers(testQuery, testIndex) }
        }

    @Test
    fun `GIVEN remote api returns players WHEN more players requested THEN expected storage to be updated`() =
        runBlocking {
            val testQuery = "test football query"
            val testIndex = 453L
            val testResponse = listOf<PlayerDomain>(mockk(), mockk(), mockk())
            every { cache.currentPlayersIndex() } returns testIndex
            every { cache.query } returns testQuery
            coEvery { remoteApi.getPlayers(testQuery, testIndex) } returns Success(testResponse)

            dataSource.getMorePlayers()

            coVerify { localStorage.updatePlayers(testResponse) }
        }

    @Test
    fun `GIVEN remote api returns error for teams WHEN more players requested THEN expected storage not updated`() =
        runBlocking {
            val testQuery = "test football query"
            val testIndex = 453L
            every { cache.playersIndexAndIncrement() } returns testIndex
            every { cache.query } returns testQuery
            coEvery { remoteApi.getTeams(testQuery) } returns ApiError()

            dataSource.getMorePlayers()

            coVerify(exactly = 0) { localStorage.updatePlayers(any()) }
        }

    @Test
    fun `GIVEN remote api returns teams WHEN more players requested THEN expected storage data returned`() =
        runBlocking {
            val testQuery = "test football query"
            val testIndex = 453L
            val testResponse = Success(listOf<PlayerDomain>(mockk(), mockk(), mockk()))
            val testDomain = Partial<PlayerDomain>(emptyList())
            every { cache.playersIndexAndIncrement() } returns testIndex
            every { cache.query } returns testQuery
            coEvery { remoteApi.getPlayers(testQuery) } returns Success(emptyList())
            coEvery { localStorage.getPlayers(testQuery) } returns testResponse
            every { mapper.mapPlayerResponse(testResponse) } returns testDomain

            assertEquals(testDomain, dataSource.getMorePlayers().players)
        }

    @Test
    fun `GIVEN remote api returns error for teams WHEN more players requested THEN expected storage data returned`() =
        runBlocking {
            val testQuery = "test football query"
            val testIndex = 453L
            val testResponse = Success(listOf<PlayerDomain>(mockk(), mockk(), mockk()))
            val testDomain = NoDomainsFound<PlayerDomain>()
            every { cache.playersIndexAndIncrement() } returns testIndex
            every { cache.query } returns testQuery
            coEvery { remoteApi.getPlayers(testQuery) } returns ApiError()
            coEvery { localStorage.getPlayers(testQuery) } returns testResponse
            every { mapper.mapPlayerResponse(testResponse) } returns testDomain

            assertEquals(testDomain, dataSource.getMorePlayers().players)
        }



    @Test
    fun `WHEN more teams requested THEN expect current query index included in request`() =
        runBlocking {
            val testQuery = "test football query"
            val testIndex = 453L
            every { cache.currentTeamsIndex() } returns testIndex
            every { cache.query } returns testQuery

            dataSource.getMoreTeams()

            coVerify { remoteApi.getTeams(testQuery, testIndex) }
        }

    @Test
    fun `GIVEN remote api returns teams WHEN more teams requested THEN expected storage to be updated`() =
        runBlocking {
            val testQuery = "test football query"
            val testIndex = 453L
            val testResponse = listOf<TeamDomain>(mockk(), mockk(), mockk())
            every { cache.currentTeamsIndex() } returns testIndex
            every { cache.query } returns testQuery
            coEvery { remoteApi.getTeams(testQuery, testIndex) } returns Success(testResponse)

            dataSource.getMoreTeams()

            coVerify { localStorage.updateTeams(testResponse) }
        }

    @Test
    fun `GIVEN remote api returns error for teams WHEN more teams requested THEN expected storage not updated`() =
        runBlocking {
            val testQuery = "test football query"
            val testIndex = 453L
            every { cache.teamsIndexAndIncrement() } returns testIndex
            every { cache.query } returns testQuery
            coEvery { remoteApi.getTeams(testQuery) } returns ApiError()

            dataSource.getMoreTeams()

            coVerify(exactly = 0) { localStorage.updateTeams(any()) }
        }

    @Test
    fun `GIVEN remote api returns teams WHEN more teams requested THEN expected storage data returned`() =
        runBlocking {
            val testQuery = "test football query"
            val testIndex = 453L
            val testResponse = Success(listOf<TeamDomain>(mockk(), mockk(), mockk()))
            val testDomain = Partial<TeamDomain>(emptyList())
            every { cache.teamsIndexAndIncrement() } returns testIndex
            every { cache.query } returns testQuery
            coEvery { remoteApi.getTeams(testQuery) } returns Success(emptyList())
            coEvery { localStorage.getTeams(testQuery, testIndex) } returns testResponse
            every { mapper.mapTeamResponse(testResponse) } returns testDomain

            assertEquals(testDomain, dataSource.getMoreTeams().teams)
        }

    @Test
    fun `GIVEN remote api returns error for teams WHEN more teams requested THEN expected storage data returned`() =
        runBlocking {
            val testQuery = "test football query"
            val testIndex = 453L
            val testResponse = Success(listOf<TeamDomain>(mockk(), mockk(), mockk()))
            val testDomain = NoDomainsFound<TeamDomain>()
            every { cache.teamsIndexAndIncrement() } returns testIndex
            every { cache.query } returns testQuery
            coEvery { remoteApi.getTeams(testQuery) } returns ApiError()
            coEvery { localStorage.getTeams(testQuery, testIndex) } returns testResponse
            every { mapper.mapTeamResponse(testResponse) } returns testDomain

            assertEquals(testDomain, dataSource.getMoreTeams().teams)
        }
}
