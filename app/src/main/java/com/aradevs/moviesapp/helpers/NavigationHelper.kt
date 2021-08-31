package com.aradevs.moviesapp.helpers

import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.aradevs.moviesapp.R
import com.aradevs.moviesapp.fragments.DislikedFragment
import com.aradevs.moviesapp.fragments.FavoritesFragment
import com.aradevs.moviesapp.fragments.PopularFragment

/***
 * Navigation helper to implement scalable routing inside the app
 * @author AraDevs
 * @property fragmentManager obtains the currentFragmentManager of the appContext
 * @constructor Creates an available NavigationHelper
 */
class NavigationHelper(private val fragmentManager: FragmentManager) {

    /**
     * Inflates a [fragment] in the specified fragment manager.
     */
    private fun openFragment(fragment: Fragment) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.main_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    /**
     * Replaces the current fragment with the one assigned to a determined [menuItem]
     * @return true as constant (required by BottomNavigationView().setOnItemSelectedListener()..).
     */
    fun navigator(menuItem: MenuItem) : Boolean{
        when (menuItem.itemId) {
            R.id.navigation_home -> openFragment(PopularFragment.newInstance())

            R.id.navigation_dashboard -> openFragment(FavoritesFragment.newInstance())

            R.id.navigation_notifications -> openFragment(DislikedFragment.newInstance())
        }
        return true
    }
}