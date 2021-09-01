package com.aradevs.moviesapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aradevs.moviesapp.R
import com.aradevs.moviesapp.helpers.AppEnvHelper
import com.aradevs.moviesapp.models.MovieModel
import com.squareup.picasso.Picasso
/***
 * Recyclerview Adapter for movie list
 * @author AraDevs
 * @property mList Obtains a list of [MovieModel]
 */
class MoviesAdapter(private val mList: List<MovieModel>) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_card, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMovie = mList[position]
        holder.textView.text = currentMovie.title
        val imageUrl : String = if (currentMovie.imageUrl != null) AppEnvHelper().getW500ImageUrl(currentMovie.imageUrl!!) else "https://us.123rf.com/450wm/pavelstasevich/pavelstasevich1811/pavelstasevich181101028/112815904-no-image-available-icon-flat-vector-illustration.jpg?ver=6"
        Picasso.get().load(imageUrl).fit().centerCrop().into(holder.moviePicture)
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }
    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.movie_card_title)
        val moviePicture: ImageView = itemView.findViewById(R.id.movie_card_img)

    }

}