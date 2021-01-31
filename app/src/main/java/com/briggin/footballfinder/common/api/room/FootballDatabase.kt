package com.briggin.footballfinder.common.api.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.briggin.footballfinder.common.api.room.player.PlayerDao
import com.briggin.footballfinder.common.api.room.player.PlayerEntity
import com.briggin.footballfinder.common.api.room.team.TeamEntity
import com.briggin.footballfinder.common.api.room.team.TeamsDao

private const val DATABASE_NAME = "listsDatabase"

@Database(entities = [TeamEntity::class, PlayerEntity::class], version = 1)
abstract class FootballDatabase : RoomDatabase() {
    abstract fun teamsDao(): TeamsDao

    abstract fun playersDao(): PlayerDao

    companion object {
        private var dbInstance: FootballDatabase? = null

        fun instance(context: Context): FootballDatabase {
            dbInstance?.let { return it }

            return Room.databaseBuilder(context, FootballDatabase::class.java, DATABASE_NAME)
                .build()
                .also { dbInstance = it }
        }
    }
}