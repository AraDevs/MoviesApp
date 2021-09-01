package com.aradevs.moviesapp.services.interfaces

import com.aradevs.moviesapp.helpers.AppEnvHelper
import com.aradevs.moviesapp.models.MovieListObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/***
 * Movies interface
 * Contains the api related methods of the Movies endpoint
 * @author AraDevs
 */
interface MoviesInterface {
    /***
     * Obtains a Call of MovieListObjects
     */
    @GET("movie/popular?")
    fun getPopularMovies(
        @Query("page") page: Int,
        @Query("language") language: String,
        @Query("api_key") apiKey: String = AppEnvHelper.API_KEY
    ): Call<MovieListObject>

}