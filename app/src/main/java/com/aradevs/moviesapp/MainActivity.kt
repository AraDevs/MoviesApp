package com.aradevs.moviesapp

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.aradevs.moviesapp.helpers.NavigationHelper

/***
 * MainScreen of the MoviesApp
 * This activity serves as a container for our BottomNavigationView
 * @author AraDevs
 */

class MainActivity : AppCompatActivity() {

    private lateinit var navView : BottomNavigationView
    private val navigationHelper = NavigationHelper(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navView = findViewById(R.id.nav_view)

        navigationHelper.navigator(navView.menu[0])

        setListeners()

    }

    private fun setListeners(){
        navView.setOnItemSelectedListener {
            menuItem -> navigationHelper.navigator(menuItem)
        }
    }

}