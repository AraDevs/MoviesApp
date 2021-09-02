@file:Suppress("DEPRECATION")

package com.aradevs.moviesapp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aradevs.moviesapp.MovieDetailActivity
import com.aradevs.moviesapp.R
import com.aradevs.moviesapp.adapters.MoviesAdapter
import com.aradevs.moviesapp.helpers.DeviceInfoHelper
import com.aradevs.moviesapp.helpers.LocalStorageHelper
import com.aradevs.moviesapp.models.MovieModel
import java.lang.Exception

/***
 * Disliked movies fragment
 * @author AraDevs
 */
class DislikedFragment : Fragment() {
    private lateinit var  moviesAdapter: MoviesAdapter
    private lateinit var recyclerView : RecyclerView
    private lateinit var emptyScreen: LinearLayout
    private var dataList = mutableListOf<MovieModel>()
    private lateinit var localStorageHelper : LocalStorageHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_disliked, container, false)
        //binding variables
        recyclerView = view.findViewById(R.id.non_favorites_recyclerview)
        emptyScreen = view.findViewById(R.id.empty_layout_non_favorites)
        localStorageHelper = LocalStorageHelper(requireActivity())
        
        fillRecyclerView()

        return view
    }

    companion object {
        fun newInstance(): DislikedFragment = DislikedFragment()
    }

    /**
     * Fills the recyclerview with the available data
     */
    private fun fillRecyclerView(){
        try {
            dataList = localStorageHelper.readFromStorage(LocalStorageHelper.NON_FAVORITES)
            if(dataList.isEmpty()){
                emptyScreen.visibility = View.VISIBLE
            }
            recyclerView.layoutManager = GridLayoutManager(requireContext(), if(DeviceInfoHelper().isTablet(requireContext())) 4 else 2)
            moviesAdapter = MoviesAdapter(dataList, ::goToMovieDetail, localStorageHelper)
            recyclerView.adapter = moviesAdapter
        }catch (exception: Exception){
            Log.e("Fragment", "NOT ATTACHED")
        }
    }

    /**
     * Launches movie detail for activity result
     */
    private fun goToMovieDetail(json: String) {
        val intent = Intent(requireContext(), MovieDetailActivity::class.java)
        intent.putExtra("movie", json)
        startActivityForResult(intent, 3)
    }

    /**
     * Re-fills recyclerview on activity result
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 3) {
            fillRecyclerView()
        }
    }
}