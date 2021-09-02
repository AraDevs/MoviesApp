package com.aradevs.moviesapp.services

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.aradevs.moviesapp.R
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

        val call = service.getPopularMovies(currentPage, language)
        if(!WebService().hasNetwork(context)) {
            Toast.makeText(context,context.getString(R.string.no_connection),Toast.LENGTH_SHORT).show()
            onSuccess(MovieListObject(movieList = mutableListOf(), page = currentPage,totalPages = 1))
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
                }else{
                    Toast.makeText(context,response.code().toString(),Toast.LENGTH_SHORT).show()
                    onSuccess(MovieListObject(movieList = mutableListOf(), page = currentPage,totalPages = 1))
                }
            }
            override fun onFailure(call: Call<MovieListObject>, t: Throwable) {
                Toast.makeText(context,t.message,Toast.LENGTH_SHORT).show()
                onSuccess(MovieListObject(movieList = mutableListOf(), page = currentPage,totalPages = 1))
            }
        })
    }
}