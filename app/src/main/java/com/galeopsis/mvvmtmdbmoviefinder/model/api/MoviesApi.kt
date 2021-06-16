package com.galeopsis.mvvmtmdbmoviefinder.model.api

import com.galeopsis.mvvmtmdbmoviefinder.model.entity.Movies
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface MoviesApi {

    @GET("460465?api_key=0d0ed127fc09b25b0e6459e907895456&language=ru-RU")
    fun getAllAsync(): Deferred<Movies>
}