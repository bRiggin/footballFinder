package com.briggin.footballfinder.api.room.player

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.briggin.footballfinder.api.room.team.TeamEntity

@Dao
interface PlayerDao {

    @Query("SELECT * FROM players WHERE firstName LIKE :query OR secondName LIKE :query LIMIT :limit")
    suspend fun getPlayers(query: String, limit: Long): List<PlayerEntity>

    @Query("SELECT * FROM players WHERE id=:id")
    suspend fun getPlayer(id: String): PlayerEntity?

    @Insert
    suspend fun newPlayer(entity: PlayerEntity)

    @Update
    suspend fun updatePlayer(entity: PlayerEntity)
}