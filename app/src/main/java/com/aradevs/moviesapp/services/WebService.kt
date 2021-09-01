package com.aradevs.moviesapp.services

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.aradevs.moviesapp.helpers.AppEnvHelper
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/***
 * Web service
 * Contains a set of Web related methods
 * @author AraDevs
 */
class WebService {

    /***
     * Returns a valid Retrofit instance
     * @param context Represents the app context
     */
    fun getRetrofit(context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppEnvHelper.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getCacheReadyClient(context))
            .build()
    }

    /***
     * Checks if the device has an available network
     * @param context Represents the app context
     */
    fun hasNetwork(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION")
            return connectivityManager.activeNetworkInfo?.isConnected ?: false
        }
    }

    /***
     * Creates an implements cache to all the requests
     * @param context Represents the app context
     */
    private fun getCacheReadyClient(context: Context) : OkHttpClient{
        val cacheSize = (5*1024*1024).toLong()
        val myCache = Cache(context.cacheDir, cacheSize)
        return OkHttpClient.Builder()
            .cache(myCache)
            .addInterceptor { chain ->
                var request = chain.request()
                request = if (hasNetwork(context))
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 10).build()
                else
                    request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
                chain.proceed(request)
            }
            .build()
    }
}