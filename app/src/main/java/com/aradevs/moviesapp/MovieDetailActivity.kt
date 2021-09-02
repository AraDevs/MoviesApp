package com.aradevs.moviesapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.aradevs.moviesapp.helpers.AppEnvHelper
import com.aradevs.moviesapp.helpers.LocalStorageHelper
import com.aradevs.moviesapp.models.MovieModel
import com.ceylonlabs.imageviewpopup.ImagePopup
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import java.util.*


class MovieDetailActivity : AppCompatActivity() {
    private lateinit var currentMovie: MovieModel
    private lateinit var movieImage: ImageView
    private lateinit var movieTitle: TextView
    private lateinit var movieOverview: TextView
    private lateinit var movieRating: TextView
    private lateinit var likeMovie: ImageView
    private lateinit var dislikeMovie: ImageView
    private lateinit var localStorageHelper: LocalStorageHelper
    private var isSorted : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        bindFields()
        parseMovie()
        displayIfSorted()
    }

    /**
     * Parses to object the movie sent via intent
     */
    private fun parseMovie() {
        try {
            currentMovie = Gson().fromJson(intent.getStringExtra("movie"), MovieModel::class.java)
            fillData()
        } catch (exception: Exception) {
            this.onBackPressed()
        }
    }

    /**
     * Binds the variables
     */
    private fun bindFields() {
        localStorageHelper =  LocalStorageHelper(this)
        movieImage = findViewById(R.id.movie_detail_img)
        movieTitle = findViewById(R.id.movie_detail_title)
        movieOverview = findViewById(R.id.movie_detail_overview)
        movieRating = findViewById(R.id.movie_detail_votes)
        likeMovie = findViewById(R.id.movie_detail_like)
        dislikeMovie = findViewById(R.id.movie_detail_dislike)
        setListeners()
    }
    /**
     * Set on click listeners to the required views
     */
    private fun setListeners() {
        movieImage.setOnClickListener {
            val imageUrl: String =
                if (currentMovie.imageUrl != null) AppEnvHelper().getW500ImageUrl(currentMovie.imageUrl!!) else AppEnvHelper.NO_IMAGE
            val imagePopup = ImagePopup(this)
            imagePopup.initiatePopupWithPicasso(imageUrl)
            imagePopup.viewPopup()
        }
        likeMovie.setOnClickListener {
            Log.e("sorted", isSorted.toString())
            if(!isSorted){
                setLiked()
            }else{
                removeLiked()
            }
            displayIfSorted()
        }
        dislikeMovie.setOnClickListener {
            if(!isSorted){
                setNotLiked()
            }else{
                removeNotLiked()
            }
            displayIfSorted()
        }
    }

    /**
     * Fills the movie information
     */
    private fun fillData() {
        movieTitle.text = currentMovie.title
        movieOverview.text = currentMovie.overview
        movieRating.text = currentMovie.voteAverage.toString()
        val imageUrl: String =
            if (currentMovie.imageUrl != null) AppEnvHelper().getW500ImageUrl(currentMovie.imageUrl!!) else "https://us.123rf.com/450wm/pavelstasevich/pavelstasevich1811/pavelstasevich181101028/112815904-no-image-available-icon-flat-vector-illustration.jpg?ver=6"
        Picasso.get().load(imageUrl).fit().centerCrop().into(movieImage)
    }
    /**
     * Checks if the movie is already sorted and displays the icon accordingly
     */
    private fun displayIfSorted(){
        isSorted = false
        likeMovie.visibility = if(localStorageHelper.existInStorage(LocalStorageHelper.FAVORITES, currentMovie)) View.VISIBLE else View.GONE
        dislikeMovie.visibility = if(localStorageHelper.existInStorage(
                LocalStorageHelper.NON_FAVORITES,
                currentMovie
            )) View.VISIBLE else View.GONE
        if(likeMovie.visibility == View.GONE && dislikeMovie.visibility == View.GONE){
            resetVisibility()
        }else{
            isSorted = true
        }
    }

    /**
     * Set a movie as liked
     */
    private fun setLiked(){
        resetVisibility()
        currentMovie.addedAt = Date()
        localStorageHelper.addToStorage(LocalStorageHelper.FAVORITES, currentMovie)
    }
    /**
     * Removes a movie like
     */
    private fun removeLiked(){
        resetVisibility()
        localStorageHelper.removeFromStorage(LocalStorageHelper.FAVORITES, currentMovie)
    }
    /**
     * Set a movie as disliked
     */
    private fun setNotLiked(){
        resetVisibility()
        currentMovie.addedAt = Date()
        localStorageHelper.addToStorage(LocalStorageHelper.NON_FAVORITES, currentMovie)
    }
    /**
     * Removes a movie dislike
     */
    private fun removeNotLiked(){
        resetVisibility()
        localStorageHelper.removeFromStorage(LocalStorageHelper.NON_FAVORITES, currentMovie)
    }
    /**
     * Resets the sorted indicators visibility
     */
    private fun resetVisibility(){
        likeMovie.visibility = View.VISIBLE
        dislikeMovie.visibility = View.VISIBLE
    }

}