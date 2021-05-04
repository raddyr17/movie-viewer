package com.raddyr.movieviewer.ui.popular

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
class PopularMoviesViewModel @Inject constructor(private val mainRepository: MovieRepository) :
    ViewModel() {

    private val _dataState: MutableLiveData<DataState<List<Movie>?>> = MutableLiveData()

    val dataState: LiveData<DataState<List<Movie>?>>
        get() = _dataState

    fun setStateEvent(popularMovieStateEvent: PopularMovieStateEvent) {
        viewModelScope.launch {
            when (popularMovieStateEvent) {
                is PopularMovieStateEvent.GetMovieEvent -> {
                    mainRepository.getPopular()
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
                PopularMovieStateEvent.None -> {}
            }
        }
    }

}


sealed class PopularMovieStateEvent {

    object GetMovieEvent : PopularMovieStateEvent()

    object None : PopularMovieStateEvent()
}