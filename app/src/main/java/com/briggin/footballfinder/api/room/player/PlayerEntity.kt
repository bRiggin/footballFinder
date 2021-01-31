package com.briggin.footballfinder.api.room.player

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.briggin.footballfinder.domain.PlayerDomain

@Entity(tableName = "players")
data class PlayerEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo
    val firstName: String,
    @ColumnInfo
    val secondName: String,
    @ColumnInfo
    val nationality: String,
    @ColumnInfo
    val age: Int,
    @ColumnInfo
    val club: String,
    @ColumnInfo
    val isFavourite: Boolean
) {

    fun toDomain() = PlayerDomain(id, firstName, secondName, nationality, age, club, isFavourite)

    companion object {
        fun fromDomain(domain: PlayerDomain) = PlayerEntity(
            domain.id,
            domain.firstName,
            domain.secondName,
            domain.nationality,
            domain.age,
            domain.club,
            domain.isFavourite
        )
    }
}
