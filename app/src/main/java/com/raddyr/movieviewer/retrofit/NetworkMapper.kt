package com.raddyr.movieviewer.retrofit

import com.raddyr.movieviewer.model.Movie
import com.raddyr.movieviewer.util.EntityMapper
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class NetworkMapper @Inject constructor() : EntityMapper<MovieNetworkEntity, Movie> {

    override fun mapFromEntity(entity: MovieNetworkEntity) = Movie(
        title = entity.title,
        posterPath = entity.posterPath,
        description = entity.overview,
        id = entity.id,
        date = entity.releaseDate?.let {
            Calendar.getInstance()
                .apply { time = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(it)!! }
                .get(Calendar.YEAR).toString()
        }
    )

    override fun mapToEntity(domainModel: Movie) = MovieNetworkEntity(
        posterPath = domainModel.posterPath,
        title = domainModel.title,
        overview = domainModel.description,
        id = domainModel.id,
        releaseDate = domainModel.date
    )

    fun mapFromEntityList(entities: List<MovieNetworkEntity>?) =
        entities?.map { mapFromEntity(it) }
}