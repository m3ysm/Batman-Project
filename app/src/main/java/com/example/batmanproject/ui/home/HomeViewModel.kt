package com.example.batmanproject.ui.home

import androidx.lifecycle.LiveData
import com.example.batmanproject.data.exception.common.NetworkException
import com.example.batmanproject.data.model.MyResponse
import com.example.batmanproject.data.model.movies.GetMovieDetailsResponseModel
import com.example.batmanproject.data.model.movies.GetMoviesResponseModel
import com.example.batmanproject.ui.base.BaseViewModel
import com.example.batmanproject.ui.home.domain.GetMovieDetailsUseCase
import com.example.batmanproject.ui.home.domain.GetMoviesUseCase
import com.example.batmanproject.util.Constants
import com.example.batmanproject.util.SingleLiveEvent
import com.example.batmanproject.util.extensions.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.core.inject

class HomeViewModel : BaseViewModel() {

    private val getMoviesUseCase: GetMoviesUseCase by inject()
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase by inject()

    private val _getMoviesLiveData =
        SingleLiveEvent<MyResponse<ArrayList<GetMoviesResponseModel>>>()
    val getMoviesLiveData: LiveData<MyResponse<ArrayList<GetMoviesResponseModel>>>
        get() = _getMoviesLiveData

    private val _getMovieDetailsLiveData =
        SingleLiveEvent<MyResponse<GetMovieDetailsResponseModel>>()
    val getMovieDetailsLiveData: LiveData<MyResponse<GetMovieDetailsResponseModel>>
        get() = _getMovieDetailsLiveData

    fun getMovies(movies: java.util.ArrayList<GetMoviesResponseModel>) {
        if (movies.isNotEmpty()) {
            _getMoviesLiveData.value = MyResponse.success(movies)
            return
        }
        _getMoviesLiveData.value = MyResponse.loading()
        sendGetMoviesRequest()
    }

    private fun sendGetMoviesRequest() {
        compositeDisposable += getMoviesUseCase.invoke(Constants.API_KEY, "batman")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _getMoviesLiveData.value = it
            }, {
                _getMoviesLiveData.value = MyResponse.failed(NetworkException())
            })
    }

    fun getMovieDetails(id: String) {
        _getMovieDetailsLiveData.value = MyResponse.loading()
        sendGetMovieDetailsRequest(id)
    }

    private fun sendGetMovieDetailsRequest(id: String) {
        compositeDisposable += getMovieDetailsUseCase.invoke(Constants.API_KEY, id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _getMovieDetailsLiveData.value = it
            }, {
                _getMovieDetailsLiveData.value = MyResponse.failed(NetworkException())
            })
    }
}