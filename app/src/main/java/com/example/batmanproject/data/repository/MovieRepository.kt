package com.example.batmanproject.data.repository

import com.example.batmanproject.data.model.ApiResponse
import com.example.batmanproject.data.model.movies.GetMovieDetailsResponseModel
import com.example.batmanproject.data.model.movies.GetMoviesResponseModel
import com.example.batmanproject.data.webservice.MovieApi
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class MovieRepository(private val rest: MovieApi) : BaseRepository() {

    fun getMovies(apikey: String, s: String)
            : Single<Response<ApiResponse<ArrayList<GetMoviesResponseModel>>>> {
        return rest.getMovies(apikey, s).subscribeOn(Schedulers.io())
    }

    fun getMovieDetails(apikey: String, i: String): Single<Response<GetMovieDetailsResponseModel>> {
        return rest.getMovieDetails(apikey, i).subscribeOn(Schedulers.io())
    }
}