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
    fun findAll(): LiveData<List<Movies>?>

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getById(id: Long): LiveData<List<Movies>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(movies: List<Movies>)

    @Query("DELETE FROM movies")
    fun deleteAllMovies()
}