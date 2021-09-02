package com.aradevs.moviesapp.helpers

import android.app.Activity
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.aradevs.moviesapp.models.MovieModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LocalStorageHelper(activity: Activity) {
    private val sharedPreferences: SharedPreferences = activity.getSharedPreferences("storage",MODE_PRIVATE)

    /**
     * Add an item to the local storage
     * @param key Represents the storage key name
     * @param currentMovie Represents the object to be added
     */
    fun addToStorage(key: String, currentMovie: MovieModel){
        val data: MutableList<MovieModel> = readFromStorage(key)
        data.add(currentMovie)
        sharedPreferences.edit().putString(key,Gson().toJson(data)).apply()
    }
    /**
     * Rebuilds the entire local storage
     * @param key Represents the storage key name
     * @param data Represents new list to be used as storage
     */
    private fun rebuildStorage(key: String, data: MutableList<MovieModel>){
        sharedPreferences.edit().putString(key,Gson().toJson(data)).apply()
    }
    /**
     * Obtains a list of objects from local storage
     * @param key Represents the storage key name
     */
    fun readFromStorage(key: String): MutableList<MovieModel>{
        val json: String? = sharedPreferences.getString(key,"[]")
        val parseTo = object : TypeToken<List<MovieModel>>() {}.type
        return Gson().fromJson(json, parseTo)
    }
    /**
     * Checks if an object already exists in the storage
     * @param key Represents the storage key name
     * @param movie Represents the object to be found
     */
    fun existInStorage(key: String,movie: MovieModel) : Boolean{
        val data: MutableList<MovieModel> = readFromStorage(key)
        return data.contains(movie)
    }
    /**
     * Removes an item from the local storage
     * @param key Represents the storage key name
     * @param movie Represents the object to be removed
     */
    fun removeFromStorage(key: String,movie: MovieModel): Boolean{
        val data: MutableList<MovieModel> = readFromStorage(key)
        val removed : Boolean = data.remove(movie)
        if(removed){
            rebuildStorage(key,data)
        }
        return  removed
    }


    companion object {
        const val FAVORITES = "favorites"
        const val NON_FAVORITES = "non_favorites"
    }
}