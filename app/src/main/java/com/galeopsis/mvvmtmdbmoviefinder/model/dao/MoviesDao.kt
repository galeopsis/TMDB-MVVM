package com.galeopsis.mvvmtmdbmoviefinder.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.galeopsis.mvvmtmdbmoviefinder.model.entity.Movies

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movies")
    fun findAll(): LiveData<List<Movies>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(users: Movies)
}