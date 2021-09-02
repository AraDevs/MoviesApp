@file:Suppress("DEPRECATION")

package com.aradevs.moviesapp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aradevs.moviesapp.MovieDetailActivity
import com.aradevs.moviesapp.R
import com.aradevs.moviesapp.adapters.MoviesAdapter
import com.aradevs.moviesapp.helpers.DeviceInfoHelper
import com.aradevs.moviesapp.helpers.LocalStorageHelper
import com.aradevs.moviesapp.models.MovieModel

/***
 * Favorite movies fragment
 * @author AraDevs
 */
class FavoritesFragment : Fragment() {
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyScreen: LinearLayout
    private var dataList = mutableListOf<MovieModel>()
    private lateinit var localStorageHelper: LocalStorageHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)
        //bind variables
        recyclerView = view.findViewById(R.id.favorites_recyclerview)
        emptyScreen = view.findViewById(R.id.empty_layout_favorites)
        localStorageHelper = LocalStorageHelper(requireActivity())

        fillRecyclerView()
        return view
    }

    companion object {
        fun newInstance(): FavoritesFragment = FavoritesFragment()
    }

    /**
     * Fills recyclerview with available data
     */
    private fun fillRecyclerView() {
        try {
            dataList = localStorageHelper.readFromStorage(LocalStorageHelper.FAVORITES)
            if (dataList.isEmpty()) {
                emptyScreen.visibility = View.VISIBLE
            }
            val list = dataList.sortedWith(compareBy({ it.title }, { it.addedAt })).toMutableList()
            recyclerView.layoutManager = GridLayoutManager(
                requireContext(),
                if (DeviceInfoHelper().isTablet(requireContext())) 4 else 2
            )
            moviesAdapter = MoviesAdapter(list, ::goToMovieDetail, localStorageHelper)
            recyclerView.adapter = moviesAdapter
        } catch (exception: Exception) {
            Log.e("Fragment", "NOT ATTACHED")
        }
    }

    /**
     * Launches movie detail for activity result
     */
    private fun goToMovieDetail(json: String) {
        val intent = Intent(requireContext(), MovieDetailActivity::class.java)
        intent.putExtra("movie", json)
        startActivityForResult(intent, 2)
    }

    /**
     * Re-fills recyclerview on activity result
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2) {
            fillRecyclerView()
        }
    }
}