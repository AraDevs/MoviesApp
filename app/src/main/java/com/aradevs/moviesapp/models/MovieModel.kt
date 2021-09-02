package com.aradevs.moviesapp.models

import com.google.gson.annotations.SerializedName
import java.util.*

/***
 * Movie model
 * Represents the serializable object of a movie
 * @author AraDevs
 */
data class MovieModel(
    @SerializedName("id") var id: Int?,
    @SerializedName("poster_path") var imageUrl : String?,
    @SerializedName("title") var title: String?,
    @SerializedName("overview") var overview: String?,
    @SerializedName("vote_average") var voteAverage : Double?,
    @SerializedName("release_date") var releaseDate: String?,
    @SerializedName("added_at") var  addedAt: Date?
)

