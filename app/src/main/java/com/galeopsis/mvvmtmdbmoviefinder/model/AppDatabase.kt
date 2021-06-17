package com.galeopsis.mvvmtmdbmoviefinder.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.galeopsis.mvvmtmdbmoviefinder.model.dao.MoviesDao
import com.galeopsis.mvvmtmdbmoviefinder.model.entity.Movies

@Database(entities = [Movies::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val moviesDao: MoviesDao
}