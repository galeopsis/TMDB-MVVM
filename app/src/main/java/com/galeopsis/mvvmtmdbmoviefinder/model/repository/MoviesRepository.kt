package com.galeopsis.mvvmtmdbmoviefinder.model.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.galeopsis.mvvmtmdbmoviefinder.model.api.MoviesApi
import com.galeopsis.mvvmtmdbmoviefinder.model.dao.MoviesDao

class MoviesRepository(private val moviesApi: MoviesApi, private val moviesDao: MoviesDao) {
    val data = moviesDao.findAll()
//    val movieId = (0..300000).random()
    val movieId = 813258
    suspend fun refresh() {
        withContext(Dispatchers.IO) {
            val movies = moviesApi.getAllAsync(movieId).await()
            moviesDao.deleteAllMovies()
            moviesDao.add(movies)
        }
    }
}