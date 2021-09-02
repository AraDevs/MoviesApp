package com.aradevs.moviesapp.models

import com.google.gson.annotations.SerializedName

/***
 * Movie list model
 * Represents the serializable object of the movie list endpoint
 * @author AraDevs
 */
data class MovieListObject(
    @SerializedName("page") var page: Int,
    @SerializedName("results") var movieList: MutableList<MovieModel>,
    @SerializedName("total_pages") var totalPages: Int,
)
