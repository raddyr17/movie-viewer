package com.raddyr.movieviewer.ui.popular.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raddyr.movieviewer.model.Movie
import com.raddyr.movieviewer.repository.MovieRepository
import com.raddyr.movieviewer.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class PopularDetailsViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {

    private val _dataState: MutableLiveData<DataState<Movie>> = MutableLiveData()

    val dataState: LiveData<DataState<Movie>>
        get() = _dataState

    fun setStateEvent(popularMovieDetailsStateEvent: PopularMovieDetailsStateEvent) {
        viewModelScope.launch {
            when (popularMovieDetailsStateEvent) {
                is PopularMovieDetailsStateEvent.GetMovieDetailsEvent -> {
                    movieRepository.getPopularDetails(popularMovieDetailsStateEvent.id)
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
                PopularMovieDetailsStateEvent.None -> {}
            }
        }
    }
}


sealed class PopularMovieDetailsStateEvent {

    data class GetMovieDetailsEvent(val id: Long) : PopularMovieDetailsStateEvent()

    object None : PopularMovieDetailsStateEvent()
}