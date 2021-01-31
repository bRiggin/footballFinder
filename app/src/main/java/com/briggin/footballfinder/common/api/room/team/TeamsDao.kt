package com.briggin.footballfinder.common.api.room.team

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TeamsDao {

    /**
     * Original query that was found to be problematic
     * SELECT * FROM teams WHERE name LIKE :query LIMIT :limit
     */
    @Query("SELECT * FROM teams")
    suspend fun getTeams(): List<TeamEntity>

    @Query("SELECT * FROM teams WHERE id=:id")
    suspend fun getTeam(id: String): TeamEntity?

    @Insert
    suspend fun newTeam(entity: TeamEntity)

    @Update
    suspend fun updateTeam(entity: TeamEntity)
}