package com.example.batmanproject.data.webservice

import com.example.batmanproject.data.model.ApiResponse
import com.example.batmanproject.data.model.movies.GetMovieDetailsResponseModel
import com.example.batmanproject.data.model.movies.GetMoviesResponseModel
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("/")
    fun getMovies(@Query("apikey") apikey: String, @Query("s") s: String)
            : Single<Response<ApiResponse<ArrayList<GetMoviesResponseModel>>>>

    @GET("/")
    fun getMovieDetails(@Query("apikey") apikey: String, @Query("i") i: String)
            : Single<Response<GetMovieDetailsResponseModel>>
}