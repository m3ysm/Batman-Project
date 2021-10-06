package com.example.batmanproject.ui.home

import androidx.lifecycle.LiveData
import com.example.batmanproject.data.exception.common.NetworkException
import com.example.batmanproject.data.model.MyResponse
import com.example.batmanproject.data.model.movies.GetMoviesResponseModel
import com.example.batmanproject.ui.base.BaseViewModel
import com.example.batmanproject.ui.home.domain.GetMoviesUseCase
import com.example.batmanproject.util.Constants
import com.example.batmanproject.util.SingleLiveEvent
import com.example.batmanproject.util.extensions.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.core.inject

class HomeViewModel : BaseViewModel() {

    private val getMoviesUseCase: GetMoviesUseCase by inject()

    private val _getMoviesLiveData =
        SingleLiveEvent<MyResponse<ArrayList<GetMoviesResponseModel>>>()
    val getMoviesLiveData: LiveData<MyResponse<ArrayList<GetMoviesResponseModel>>>
        get() = _getMoviesLiveData

    fun getMovies() {
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
}