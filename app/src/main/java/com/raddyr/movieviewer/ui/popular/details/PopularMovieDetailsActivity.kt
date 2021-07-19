package com.raddyr.movieviewer.ui.popular.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.raddyr.movieviewer.R
import com.raddyr.movieviewer.databinding.PopularMovieDetailsActivityBinding
import com.raddyr.movieviewer.model.Movie
import com.raddyr.movieviewer.util.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PopularMovieDetailsActivity : AppCompatActivity() {


    private lateinit var binding: PopularMovieDetailsActivityBinding

    private val viewModel: PopularDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PopularMovieDetailsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        subscribeObservers()
        viewModel.setStateEvent(
            PopularMovieDetailsStateEvent.GetMovieDetailsEvent(
                intent.getLongExtra(
                    ID,
                    0
                )
            )
        )
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this, Observer { dataState ->
            when (dataState) {
                is DataState.Success<Movie> -> {
                    displayProgressBar(false)
                    setViews(dataState.data)
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(dataState.exception.message)
                }
                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })
    }

    private fun setViews(movie: Movie) {
        with(binding) {
            title.text = movie.title
            description.text = movie.description
            year.text = String.format(getString(R.string.release_date), movie.date)
            Glide.with(this@PopularMovieDetailsActivity)
                .load(IMAGE_URL + movie.posterPath)
                .centerCrop()
                .into(image)
        }
    }

    private fun displayError(message: String?) {
        if (message != null) binding.error.text = message else binding.error.text =
            getString(R.string.unknown_error)
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        binding.progressBar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

    companion object {
        const val IMAGE_URL = "https://image.tmdb.org/t/p/w185/"
        private const val ID = "ID"
    }
}