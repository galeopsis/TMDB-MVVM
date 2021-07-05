package com.galeopsis.mvvmtmdbmoviefinder.model.api

import com.galeopsis.mvvmtmdbmoviefinder.BuildConfig
import com.galeopsis.mvvmtmdbmoviefinder.model.entity.Movies
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

private const val api_key = BuildConfig.API_KEY

interface MoviesApi {

    @GET("{movieId}?api_key=$api_key&language=ru-RU")
    fun getAllAsync(@Path("movieId") movieId: Int): Deferred<Movies>

    @GET("popular?api_key=$api_key&language=ru-RU")
    fun getAllPopularAsync(): Deferred<List<Movies>>
}