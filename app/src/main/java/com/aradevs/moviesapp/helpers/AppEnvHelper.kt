package com.aradevs.moviesapp.helpers
/***
 * App Env Class
 * Contains a set of useful constants that might be referenced inside the app
 * @author AraDevs
 */
class AppEnvHelper {

    private val imageBaseUrl: String = "https://image.tmdb.org/t/p/w500"

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "8a46a6480af6dbe40ff2e1931926f0f4"
    }

    /***
     * Returns a full url based on themoviedb image path standard
     * @param modifier Represents an valid image url modifier
     */
    fun getW500ImageUrl(modifier: String): String{
        return imageBaseUrl+modifier
    }
}