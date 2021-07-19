package com.raddyr.movieviewer.ui.popular

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.raddyr.movieviewer.databinding.MovieListItemBinding
import com.raddyr.movieviewer.model.Movie


class PopularAdapter(private val list: List<Movie>, val listener: (id: Long?) -> Unit) :
    RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {

    inner class PopularViewHolder(private val binding: MovieListItemBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(context: Context, item: Movie) {
            Glide.with(context)
                .load(IMAGE_URL + item.posterPath)
                .centerCrop()
                .into(binding.image)
            binding.title.text = item.title
            binding.root.setOnClickListener {
                listener.invoke(item.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PopularViewHolder(
        MovieListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ), parent.context
    )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        holder.bindView(holder.context, list[position])
    }

    companion object {
        const val IMAGE_URL = "https://image.tmdb.org/t/p/w185/"
    }
}