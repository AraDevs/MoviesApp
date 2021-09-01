package com.aradevs.moviesapp.services

import android.content.Context
import android.util.Log
import com.aradevs.moviesapp.models.MovieListObject
import com.aradevs.moviesapp.services.interfaces.MoviesInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/***
 * Movies Service
 * Contains a set of methods used to get information from the API
 * @property context Represents the context of the app
 * @property service Represents the HttpClient to be used
 * @author AraDevs
 */
class MoviesService(
    private val context: Context,
    private val service: MoviesInterface = WebService().getRetrofit(context)
        .create(MoviesInterface::class.java)
) {

    /***
     * Obtains a list of popular movies from the API
     * @param currentPage Represents the page to be requested
     * @param  language Represents the locale of the request
     * @param onSuccess represents the function to be executed after the user input
     */
    fun getPopularMovies(currentPage: Int, language: String = "es_ES", onSuccess: (movieList: MovieListObject) -> Unit){
        ///TODO: Remove the logs of this method when fully tested
        val call = service.getPopularMovies(currentPage, language)
        if(!WebService().hasNetwork(context)) {
            Log.e("NETWORK", "NO NETWORK")
        }
        call.enqueue(object : Callback<MovieListObject> {
            override fun onResponse(
                call: Call<MovieListObject>,
                response: Response<MovieListObject>
            ) {
                if (response.code() == 200) {
                    val moviesResponse = response.body()!!
                    for (movie in moviesResponse.movieList) {
                        Log.e("MOVIE", movie.title!!)
                    }
                    onSuccess(moviesResponse)
                }
            }
            override fun onFailure(call: Call<MovieListObject>, t: Throwable) {
                Log.e("ERROR", t.message.toString())
            }
        })
    }
}