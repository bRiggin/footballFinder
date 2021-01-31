package com.briggin.footballfinder.common.api.room.team

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.briggin.footballfinder.common.domain.TeamDomain

@Entity(tableName = "teams")
data class TeamEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo
    val name: String,
    @ColumnInfo
    val city: String,
    @ColumnInfo
    val stadium: String,
    @ColumnInfo
    val nationality: String
) {
    fun toDomain() = TeamDomain(id, name, city, stadium, nationality)

    companion object {
        fun fromDomain(domain: TeamDomain) = TeamEntity(
            domain.id, domain.name, domain.city, domain.stadium, domain.nationality
        )
    }
}
