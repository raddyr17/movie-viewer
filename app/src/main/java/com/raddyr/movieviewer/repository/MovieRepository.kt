package com.raddyr.movieviewer.repository

import com.raddyr.movieviewer.model.Movie
import com.raddyr.movieviewer.retrofit.NetworkMapper
import com.raddyr.movieviewer.retrofit.TheMovieDbRetrofit
import com.raddyr.movieviewer.util.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val networkMapper: NetworkMapper,
    private val retrofit: TheMovieDbRetrofit
) {
    suspend fun getPopularList(): Flow<DataState<List<Movie>?>> = flow {
        emit(DataState.Loading)
        delay(1000)
        try {
            val popular = retrofit.popular()
            val movies: List<Movie>? = networkMapper.mapFromEntityList(popular.results)
            emit(DataState.Success(movies))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun getPopularDetails(id: Long): Flow<DataState<Movie>> = flow {
        emit(DataState.Loading)
        delay(1000)
        try {
            val details = retrofit.details(id)
            val movie = networkMapper.mapFromEntity(details)
            emit(DataState.Success(movie))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}