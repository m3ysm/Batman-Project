package com.example.batmanproject.ui.home.domain

import com.example.batmanproject.data.exception.common.NetworkException
import com.example.batmanproject.data.model.ApiResponse
import com.example.batmanproject.data.model.MyResponse
import com.example.batmanproject.data.model.movies.GetMoviesResponseModel
import com.example.batmanproject.data.repository.MovieRepository
import io.reactivex.Single
import retrofit2.Response

class GetMoviesUseCase(private val repository: MovieRepository) {

    fun invoke(apikey: String, s: String): Single<MyResponse<ArrayList<GetMoviesResponseModel>>> {
        return repository.getMovies(apikey, s).flatMap { handleResponse(it) }
    }

    private fun handleResponse(response: Response<ApiResponse<ArrayList<GetMoviesResponseModel>>>): Single<MyResponse<ArrayList<GetMoviesResponseModel>>> {
        return if (response.isSuccessful) {
            Single.just(MyResponse.success(response.body()?.search!!))
        } else {
            Single.just(MyResponse.failed(NetworkException()))
        }
    }
}