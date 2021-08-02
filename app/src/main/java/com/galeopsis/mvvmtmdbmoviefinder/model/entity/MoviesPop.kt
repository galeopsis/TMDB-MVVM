package com.galeopsis.mvvmtmdbmoviefinder.model.entity

data class MoviesPop(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)