@file:Suppress("DEPRECATION")

package com.aradevs.moviesapp

import android.content.Context
import android.os.Bundle
import android.os.LocaleList
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.aradevs.moviesapp.helpers.LocalStorageHelper
import com.aradevs.moviesapp.helpers.NavigationHelper
import java.util.*

/***
 * MainScreen of the MoviesApp
 * This activity serves as a container for our BottomNavigationView
 * @author AraDevs
 */

class MainActivity : AppCompatActivity() {

    private lateinit var navView : BottomNavigationView
    private lateinit var localeButton: Button
    private lateinit var localStorageHelper: LocalStorageHelper
    private val navigationHelper = NavigationHelper(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLocale()
        setContentView(R.layout.activity_main)
        navView = findViewById(R.id.nav_view)
        navigationHelper.navigator(navView.menu[0])
        localeButton = findViewById(R.id.localeButton)
        localStorageHelper = LocalStorageHelper(this)
        setLocaleDialog()
        setListeners()

    }

    /***
     * Sets the selected locale for the whole app
     * Currently supported: EN, ES
     */
    private fun setLocale(localeToSet: String) {
        val localeListToSet = LocaleList(Locale(localeToSet))
        LocaleList.setDefault(localeListToSet)
        resources.configuration.setLocales(localeListToSet)
        resources.updateConfiguration(resources.configuration, resources.displayMetrics)
            val sharedPref = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
            sharedPref.putString(LocalStorageHelper.LOCALE, localeToSet)
        sharedPref.apply()
    }

    /***
     * Loads the current locale
     */
    private fun loadLocale() {
        val sharedPref = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val locale = sharedPref.getString(LocalStorageHelper.LOCALE, "es")
        val localeToSet: String = locale!!
        setLocale(localeToSet)
    }
    /***
     * Sets the on click listeners
     */
    private fun setListeners(){
        navView.setOnItemSelectedListener {
            menuItem -> navigationHelper.navigator(menuItem)
        }
    }

    /***
     * Inflates a dialog with the current supported locale options
     */
    private fun setLocaleDialog(){
        localeButton.setOnClickListener {
            val languages = arrayOf("Español","English")
            val langSelectorBuilder = AlertDialog.Builder(this@MainActivity)
            langSelectorBuilder.setTitle(getString(R.string.choose_language))
            langSelectorBuilder.setSingleChoiceItems(languages, -1) { dialog, selection ->
                when(selection) {
                    0 -> {
                        setLocale("es")
                    }
                    1 -> {
                        setLocale("en")
                    }
                }
                recreate()
                dialog.dismiss()
            }
            langSelectorBuilder.create().show()
        }
    }
}