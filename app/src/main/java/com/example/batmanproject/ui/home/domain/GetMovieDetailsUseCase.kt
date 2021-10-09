package com.example.batmanproject.ui.home.domain

import com.example.batmanproject.data.exception.common.NetworkException
import com.example.batmanproject.data.model.MyResponse
import com.example.batmanproject.data.model.movies.GetMovieDetailsResponseModel
import com.example.batmanproject.data.repository.MovieRepository
import io.reactivex.Single
import retrofit2.Response

class GetMovieDetailsUseCase(private val repository: MovieRepository) {

    fun invoke(apikey: String, i: String): Single<MyResponse<GetMovieDetailsResponseModel>> {
        return repository.getMovieDetails(apikey, i).flatMap { handleResponse(it) }
    }

    private fun handleResponse(response: Response<GetMovieDetailsResponseModel>): Single<MyResponse<GetMovieDetailsResponseModel>> {
        return if (response.isSuccessful) {
            Single.just(MyResponse.success(response.body()!!))
        } else {
            Single.just(MyResponse.failed(NetworkException()))
        }
    }
}