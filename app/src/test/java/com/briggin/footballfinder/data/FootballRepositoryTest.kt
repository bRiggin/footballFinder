package com.briggin.footballfinder.data

import com.briggin.footballfinder.domain.FootballDataSource
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FootballRepositoryTest {

    private val localApi: FootballApi = mockk()
    private val remoteApi: FootballApi = mockk()

    private lateinit var dataSource: FootballDataSource

    @Before
    fun `configure test`() {
        dataSource = FootballRepository(localApi, remoteApi)
    }

    @Test
    fun `sanity check`() {
        assertEquals(4, 2+2)
    }
}
