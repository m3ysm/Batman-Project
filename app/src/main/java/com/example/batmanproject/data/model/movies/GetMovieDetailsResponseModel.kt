package com.example.batmanproject.data.model.movies

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GetMovieDetailsResponseModel(
    @SerializedName("Title")
    val title: String,
    @SerializedName("Year")
    val year: String,
    @SerializedName("Rated")
    val rated: String,
    @SerializedName("Released")
    val released: String,
    @SerializedName("Runtime")
    val runtime: String,
    @SerializedName("Genre")
    val genre: String,
    @SerializedName("Director")
    val director: String,
    @SerializedName("Writer")
    val writer: String,
    @SerializedName("Actors")
    val actors: String,
    @SerializedName("Plot")
    val plot: String,
    @SerializedName("Language")
    val language: String,
    @SerializedName("Country")
    val country: String,
    @SerializedName("Awards")
    val awards: String,
    @SerializedName("Poster")
    val poster: String,
    @SerializedName("Ratings")
    val ratings: ArrayList<Ratings>,
    @SerializedName("Metascore")
    val metascore: String,
    @SerializedName("imdbRating")
    val imdbRating: Double,
    @SerializedName("imdbVotes")
    val imdbVotes: String,
    @SerializedName("imdbID")
    val imdbID: String,
    @SerializedName("Type")
    val type: String,
    @SerializedName("DVD")
    val dVD: String,
    @SerializedName("BoxOffice")
    val boxOffice: String,
    @SerializedName("Production")
    val production: String,
    @SerializedName("Website")
    val website: String,
    @SerializedName("Response")
    val response: Boolean
) : Serializable
