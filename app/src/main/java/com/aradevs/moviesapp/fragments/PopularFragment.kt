package com.aradevs.moviesapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aradevs.moviesapp.R
import com.aradevs.moviesapp.adapters.MoviesAdapter
import com.aradevs.moviesapp.helpers.DeviceInfoHelper
import com.aradevs.moviesapp.helpers.PaginationHelper
import com.aradevs.moviesapp.models.MovieListObject
import com.aradevs.moviesapp.models.MovieModel
import com.aradevs.moviesapp.services.MoviesService
import java.lang.Exception

/***
 * Popular movies fragment
 * @author AraDevs
 */
class PopularFragment : Fragment() {
    private lateinit var showPageDialog : Button
    private  var currentPage: Int = 1
    private lateinit var  moviesAdapter: MoviesAdapter
    private lateinit var recyclerView : RecyclerView
    private var dataList = mutableListOf<MovieModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_popular, container, false)
        showPageDialog = view.findViewById(R.id.testBtn)
        recyclerView = view.findViewById(R.id.popular_recyclerview)
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
    private fun setListeners(maxPages: Int){
        showPageDialog.setOnClickListener {
            PaginationHelper(requireActivity(), maxPages, currentPage).showDialog(::getMovieList)
        }
    }

    /***
     * Calls the API for all the popular movies and updates based on a current page
     * @param currentPage Represents the page to be requested
     */
    private fun getMovieList(currentPage: Int){
        this.currentPage = currentPage
        updateButtonCounter(currentPage)
        MoviesService(requireContext()).getPopularMovies(currentPage,"es_ES" ,::manageMovieList)
    }

    /***
     * Method to be executed onSuccess of [getMovieList], triggers [setListeners], updates [dataList] and fills recyclerview
     * @param data Represents the movie list object
     */
    private fun manageMovieList(data: MovieListObject){
        setListeners(data.totalPages)
        dataList = data.movieList
        fillRecyclerView()
    }

    /***
     * Updates the [showPageDialog] button with the current page
     * @param currentPage Represents the page requested to theserver
     */
    private fun updateButtonCounter(currentPage: Int){
        showPageDialog.text = currentPage.toString()
    }

    /**
     * Process the [dataList] and displays the movie list
     */
    private fun fillRecyclerView(){
        try {
            recyclerView.layoutManager = GridLayoutManager(requireContext(), if(DeviceInfoHelper().isTablet(requireContext())) 4 else 2)
            moviesAdapter = MoviesAdapter(dataList)
            recyclerView.adapter = moviesAdapter
        }catch (exception: Exception){
            Log.e("Fragment", "NOT ATTACHED")
        }
    }

}