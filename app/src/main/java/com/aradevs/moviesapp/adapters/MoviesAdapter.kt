package com.aradevs.moviesapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.aradevs.moviesapp.R
import com.aradevs.moviesapp.helpers.AppEnvHelper
import com.aradevs.moviesapp.helpers.LocalStorageHelper
import com.aradevs.moviesapp.models.MovieModel
import com.github.thunder413.datetimeutils.DateTimeUtils
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import java.io.Serializable

/***
 * Recyclerview Adapter for movie list
 * @author AraDevs
 * @property mList Obtains a list of [MovieModel]
 */
class MoviesAdapter(private val mList: MutableList<MovieModel>, private val goToDetail: (json: String)->Unit, private val localStorageHelper: LocalStorageHelper) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>(), Serializable {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_card, parent, false)
        return ViewHolder(view)
    }

    // binds the list items to a view
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMovie = mList[position]
        holder.setIsRecyclable(false)
        holder.textView.text = currentMovie.title
        holder.releaseDate.text = DateTimeUtils.formatWithPattern(currentMovie.releaseDate, "dd-MM-yyyy")
        if(localStorageHelper.existInStorage(LocalStorageHelper.FAVORITES, currentMovie)){
            holder.movieCardIndicator.visibility = View.VISIBLE
            holder.movieCardStatus.setImageDrawable(holder.itemView.context.getDrawable(R.drawable.ic_baseline_thumb_up_alt_24))
        }
        if(localStorageHelper.existInStorage(LocalStorageHelper.NON_FAVORITES, currentMovie)){
            holder.movieCardIndicator.visibility = View.VISIBLE
            holder.movieCardStatus.setImageDrawable(holder.itemView.context.getDrawable(R.drawable.ic_baseline_thumb_down_alt_24))
        }
        val imageUrl : String = if (currentMovie.imageUrl != null) AppEnvHelper().getW500ImageUrl(
            currentMovie.imageUrl!!
        ) else AppEnvHelper.NO_IMAGE
        Picasso.get().load(imageUrl).fit().centerCrop().into(holder.moviePicture)
        holder.movieContainer.setOnClickListener {
            goToDetail(Gson().toJson(currentMovie))
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    //test
    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.movie_card_title)
        val moviePicture: ImageView = itemView.findViewById(R.id.movie_card_img)
        val movieContainer: CardView = itemView.findViewById(R.id.movie_container)
        val releaseDate: TextView = itemView.findViewById(R.id.movie_card_date)
        val movieCardIndicator: CardView = itemView.findViewById(R.id.movie_card_indicator)
        val movieCardStatus: ImageView = itemView.findViewById(R.id.movie_card_status)
    }

}