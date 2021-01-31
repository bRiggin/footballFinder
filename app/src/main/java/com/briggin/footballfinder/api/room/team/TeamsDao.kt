package com.briggin.footballfinder.api.room.team

import androidx.room.*

@Dao
interface TeamsDao {

    @Query("SELECT * FROM teams WHERE name LIKE :query LIMIT :limit")
    suspend fun getTeams(query: String, limit: Long): List<TeamEntity>

    @Query("SELECT * FROM teams WHERE id=:id")
    suspend fun getTeam(id: String): TeamEntity?

    @Insert
    suspend fun newTeam(entity: TeamEntity)

    @Update
    suspend fun updateTeam(entity: TeamEntity)
}