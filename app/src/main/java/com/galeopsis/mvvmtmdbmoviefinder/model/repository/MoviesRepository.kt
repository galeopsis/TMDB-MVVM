package com.galeopsis.mvvmtmdbmoviefinder.model.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.galeopsis.mvvmtmdbmoviefinder.model.api.MoviesApi
import com.galeopsis.mvvmtmdbmoviefinder.model.dao.MoviesDao

class MoviesRepository(private val moviesApi: MoviesApi, private val moviesDao: MoviesDao) {

    val data = moviesDao.findAll()

    suspend fun refresh() {
        withContext(Dispatchers.IO) {
            val movies = moviesApi.getAllAsync().await()
            moviesDao.add(movies)
        }
    }
}