package com.raddyr.movieviewer.retrofit

import retrofit2.http.GET

interface TheMovieDbRetrofit {

    @GET("movie/popular")
    suspend fun popular(): MovieListResponse
}