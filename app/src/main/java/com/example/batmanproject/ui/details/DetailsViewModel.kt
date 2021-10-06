package com.example.batmanproject.ui.details

import androidx.lifecycle.LiveData
import com.example.batmanproject.data.exception.common.NetworkException
import com.example.batmanproject.data.model.MyResponse
import com.example.batmanproject.data.model.movies.GetMovieDetailsResponseModel
import com.example.batmanproject.ui.base.BaseViewModel
import com.example.batmanproject.ui.details.domain.GetMovieDetailsUseCase
import com.example.batmanproject.util.Constants
import com.example.batmanproject.util.SingleLiveEvent
import com.example.batmanproject.util.extensions.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.core.inject

class DetailsViewModel : BaseViewModel() {

    private val getMovieDetailsUseCase: GetMovieDetailsUseCase by inject()

    private val _getMovieDetailsLiveData =
        SingleLiveEvent<MyResponse<GetMovieDetailsResponseModel>>()
    val getMovieDetailsLiveData: LiveData<MyResponse<GetMovieDetailsResponseModel>>
        get() = _getMovieDetailsLiveData

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