package com.aradevs.moviesapp.helpers

import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import com.aradevs.moviesapp.models.MovieModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LocalStorageHelper(private val activity: Activity) {
    private val sharedPreferences: SharedPreferences =
        activity.getSharedPreferences("storage", MODE_PRIVATE)

    /**
     * Add an item to the local storage
     * @param key Represents the storage key name
     * @param currentMovie Represents the object to be added
     */
    fun addToStorage(key: String, currentMovie: MovieModel) {
        val data: MutableList<MovieModel> = readFromStorage(key)
        data.add(currentMovie)
        Log.e("added", currentMovie.id.toString())
        sharedPreferences.edit().putString(key, Gson().toJson(data)).apply()
    }

    /**
     * Rebuilds the entire local storage
     * @param key Represents the storage key name
     * @param data Represents new list to be used as storage
     */
    private fun rebuildStorage(key: String, data: MutableList<MovieModel>) {
        sharedPreferences.edit().putString(key, Gson().toJson(data)).apply()
    }

    /**
     * Obtains a list of objects from local storage
     * @param key Represents the storage key name
     */
    fun readFromStorage(key: String): MutableList<MovieModel> {
        val json: String? = sharedPreferences.getString(key, "[]")
        val parseTo = object : TypeToken<List<MovieModel>>() {}.type
        return Gson().fromJson(json, parseTo)
    }

    /**
     * Checks if an object already exists in the storage
     * @param key Represents the storage key name
     * @param movie Represents the object to be found
     */
    fun existInStorage(key: String, movie: MovieModel): Boolean {
        val data: MutableList<MovieModel> = readFromStorage(key)
        return data.count { it.id == movie.id } > 0
    }

    /**
     * Removes an item from the local storage
     * @param key Represents the storage key name
     * @param movie Represents the object to be removed
     */
    fun removeFromStorage(key: String, movie: MovieModel): Boolean {
        val data: MutableList<MovieModel> = readFromStorage(key)
        val removed: Boolean = data.remove(data.first { it.id == movie.id })
        Log.e("removed",removed.toString())
        if (removed) {
            rebuildStorage(key, data)
        }
        return removed
    }
    /**
     * Returns the current string locale
     */
    fun getCurrentLocale(): String {
        val sharedPref = activity.getSharedPreferences("Settings", MODE_PRIVATE)
        return sharedPref.getString(LOCALE, "es")!!
    }


    companion object {
        const val FAVORITES = "favorites"
        const val NON_FAVORITES = "non_favorites"
        const val LOCALE = "locale"
    }
}