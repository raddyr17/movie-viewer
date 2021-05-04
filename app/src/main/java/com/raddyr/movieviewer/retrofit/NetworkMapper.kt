package com.raddyr.movieviewer.retrofit

import com.raddyr.movieviewer.model.Movie
import com.raddyr.movieviewer.util.EntityMapper
import javax.inject.Inject

class NetworkMapper @Inject constructor(): EntityMapper<MovieNetworkEntity, Movie> {

    override fun mapFromEntity(entity: MovieNetworkEntity) = Movie(
        title = entity.title,
        posterPath = entity.posterPath
    )

    override fun mapToEntity(domainModel: Movie) = MovieNetworkEntity(
        posterPath = domainModel.posterPath,
        title = domainModel.title
    )

    fun mapFromEntityList(entities: List<MovieNetworkEntity>?) =
        entities?.map { mapFromEntity(it) }
}