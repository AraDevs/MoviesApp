@file:Suppress("DEPRECATION")

package com.aradevs.moviesapp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aradevs.moviesapp.MovieDetailActivity
import com.aradevs.moviesapp.R
import com.aradevs.moviesapp.adapters.MoviesAdapter
import com.aradevs.moviesapp.helpers.DeviceInfoHelper
import com.aradevs.moviesapp.helpers.LocalStorageHelper
import com.aradevs.moviesapp.helpers.PaginationHelper
import com.aradevs.moviesapp.models.MovieListObject
import com.aradevs.moviesapp.models.MovieModel
import com.aradevs.moviesapp.services.MoviesService
import com.google.android.material.progressindicator.LinearProgressIndicator

/***
 * Popular movies fragment
 * @author AraDevs
 */

class PopularFragment : Fragment() {
    private lateinit var showPageDialog: Button
    private var currentPage: Int = 1
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyScreen: LinearLayout
    private lateinit var retryButton: Button
    private lateinit var loading: LinearProgressIndicator
    private var dataList = mutableListOf<MovieModel>()
    private lateinit var localStorageHelper: LocalStorageHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_popular, container, false)
        showPageDialog = view.findViewById(R.id.testBtn)
        recyclerView = view.findViewById(R.id.popular_recyclerview)
        emptyScreen = view.findViewById(R.id.empty_layout)
        retryButton = view.findViewById(R.id.empty_retry_btn)
        loading = view.findViewById(R.id.popular_loading)
        localStorageHelper = LocalStorageHelper(requireActivity())
        updateButtonCounter(currentPage)
        getMovieList(currentPage)
        return view
    }

    companion object {
        fun newInstance(): PopularFragment = PopularFragment()
    }

    /***
     * Sets onclick listeners to this fragments components (when needed)
     * @param maxPages Represents the max pages to be referenced to [PaginationHelper]
     */
    private fun setListeners(maxPages: Int) {
        showPageDialog.setOnClickListener {
            PaginationHelper(requireActivity(), maxPages, currentPage).showDialog(::getMovieList)
        }
        retryButton.setOnClickListener {
            emptyScreen.visibility = View.GONE
            getMovieList(currentPage)
        }
    }

    /***
     * Calls the API for all the popular movies and updates based on a current page
     * @param currentPage Represents the page to be requested
     */
    private fun getMovieList(currentPage: Int) {
        this.currentPage = currentPage
        updateButtonCounter(currentPage)
        loading.visibility = View.VISIBLE
        MoviesService(requireContext()).getPopularMovies(currentPage, localStorageHelper.getCurrentLocale(), ::manageMovieList)

    }

    /***
     * Method to be executed onSuccess of [getMovieList], triggers [setListeners], updates [dataList] and fills recyclerview
     * @param data Represents the movie list object
     */
    private fun manageMovieList(data: MovieListObject) {
        setListeners(data.totalPages)
        loading.visibility = View.GONE
        dataList = data.movieList
        if(dataList.isEmpty()){
            emptyScreen.visibility = View.VISIBLE
        }
        fillRecyclerView()
    }

    /***
     * Updates the [showPageDialog] button with the current page
     * @param currentPage Represents the page requested to theserver
     */
    private fun updateButtonCounter(currentPage: Int) {
        showPageDialog.text = currentPage.toString()
    }

    /**
     * Process the [dataList] and displays the movie list
     */
    private fun fillRecyclerView() {
        try {
            recyclerView.layoutManager = GridLayoutManager(
                requireContext(),
                if (DeviceInfoHelper().isTablet(requireContext())) 4 else 2
            )
            moviesAdapter = MoviesAdapter(dataList, ::goToMovieDetail, localStorageHelper)
            recyclerView.adapter = moviesAdapter
        } catch (exception: Exception) {
            Log.e("Fragment", "NOT ATTACHED")
        }
    }
    /**
     * Launches movie detail for activity result
     */
    private fun goToMovieDetail(json: String) {
        val intent = Intent(requireContext(),MovieDetailActivity::class.java)
        intent.putExtra("movie", json)
        startActivityForResult(intent, 1)
    }
    /**
     * Re-fills recyclerview on activity result
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            Log.e("Result", "result")
            moviesAdapter.notifyDataSetChanged()
        }
    }

}