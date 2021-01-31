package com.briggin.footballfinder.common.api.room.player

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PlayerDao {
    /**
     * Original query that was found to be problematic
     * SELECT * FROM players WHERE firstName LIKE :query OR secondName LIKE :query LIMIT :limit
     */
    @Query("SELECT * FROM players")
    suspend fun getPlayers(): List<PlayerEntity>

    @Query("SELECT * FROM players WHERE isFavourite")
    suspend fun getFavouritePlayers(): List<PlayerEntity>

    @Query("SELECT * FROM players WHERE id=:id")
    suspend fun getPlayer(id: String): PlayerEntity?

    @Insert
    suspend fun newPlayer(entity: PlayerEntity)

    @Update
    suspend fun updatePlayer(entity: PlayerEntity)
}