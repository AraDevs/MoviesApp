package com.aradevs.moviesapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aradevs.moviesapp.R

/***
 * Favorite movies fragment
 * @author AraDevs
 */
class FavoritesFragment : Fragment() {
    //private lateinit var  favoritesAdapter: MoviesAdapter
    private lateinit var recyclerView : RecyclerView
    //private var dataList = mutableListOf<MovieModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)
        recyclerView = view.findViewById(R.id.favorites_recyclerview)
        return view
    }

    companion object {
        fun newInstance(): FavoritesFragment = FavoritesFragment()
    }
}