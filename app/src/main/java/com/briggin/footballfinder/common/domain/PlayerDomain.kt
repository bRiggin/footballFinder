package com.briggin.footballfinder.common.domain

data class PlayerDomain(
    val id: String,
    val firstName: String,
    val secondName: String,
    val nationality: String,
    val age: Int?,
    val club: String,
    val isFavourite: Boolean = false
)
