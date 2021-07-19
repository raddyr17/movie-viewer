package com.raddyr.movieviewer.retrofit

import retrofit2.http.GET
import retrofit2.http.Path

interface TheMovieDbRetrofit {

    @GET("movie/popular")
    suspend fun popular(): MovieListResponse


    @GET("movie/{id}")
    suspend fun details(@Path("id") id: Long): MovieNetworkEntity
}