package com.galeopsis.mvvmtmdbmoviefinder.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movies(
    @PrimaryKey val id: Int,
    val title: String,
    val release_date: String,
    val vote_average: Double,
    val overview: String,
    val poster_path: String,
    val adult: Boolean
)