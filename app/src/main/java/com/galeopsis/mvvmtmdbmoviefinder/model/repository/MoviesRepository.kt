package com.galeopsis.mvvmtmdbmoviefinder.model.repository

import com.galeopsis.mvvmtmdbmoviefinder.model.api.MoviesApi
import com.galeopsis.mvvmtmdbmoviefinder.model.dao.MoviesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepository(private val moviesApi: MoviesApi, private val moviesDao: MoviesDao) {
    val data = moviesDao.findAll()
    //        private val movieId = (0..950000).random()
    private val movieId = 726684

    suspend fun refresh() {
        withContext(Dispatchers.IO) {
            val movies = moviesApi.getAllAsync(movieId).await()
//            moviesDao.deleteAllMovies()
            moviesDao.add(movies)
        }
    }
}