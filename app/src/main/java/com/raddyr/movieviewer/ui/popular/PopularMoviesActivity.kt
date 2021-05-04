package com.raddyr.movieviewer.ui.popular

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.raddyr.movieviewer.R
import com.raddyr.movieviewer.databinding.PopularMoviesActivityBinding
import com.raddyr.movieviewer.model.Movie
import com.raddyr.movieviewer.util.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PopularMoviesActivity : AppCompatActivity() {

    private lateinit var binding: PopularMoviesActivityBinding

    private val viewModel: PopularMoviesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PopularMoviesActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        subscribeObservers()
        viewModel.setStateEvent(PopularMovieStateEvent.GetMovieEvent)
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this, Observer { dataState ->
            when (dataState) {
                is DataState.Success<List<Movie>?> -> {
                    displayProgressBar(false)
                    setAdapter(dataState.data!!)
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

    private fun setAdapter(movies: List<Movie>) {
        binding.recycler.adapter = PopularAdapter(movies)
        binding.recycler.layoutManager = GridLayoutManager(this, 2)
    }

    private fun displayError(message: String?) {
        if (message != null) binding.error.text = message else binding.error.text =
            getString(R.string.unknown_error)
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        binding.progressBar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }
}
