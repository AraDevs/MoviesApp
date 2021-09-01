package com.aradevs.moviesapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aradevs.moviesapp.R

/***
 * Disliked movies fragment
 * @author AraDevs
 */
class DislikedFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_disliked, container, false)
    }

    companion object {
        fun newInstance(): DislikedFragment = DislikedFragment()
    }

}