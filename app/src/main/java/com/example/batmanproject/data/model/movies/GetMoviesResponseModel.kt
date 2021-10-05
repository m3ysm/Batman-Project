package com.example.batmanproject.data.model.movies

import com.google.gson.annotations.SerializedName

data class GetMoviesResponseModel(
    @SerializedName("Title")
    val title: String,
    @SerializedName("Year")
    val year: Int,
    @SerializedName("imdbID")
    val imdbID: String,
    @SerializedName("Type")
    val type: String,
    @SerializedName("Poster")
    val poster: String
)
