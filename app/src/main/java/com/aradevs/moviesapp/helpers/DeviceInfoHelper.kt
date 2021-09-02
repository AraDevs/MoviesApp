package com.aradevs.moviesapp.helpers

import android.content.Context
import android.content.res.Configuration

/***
 * Device info helper
 *
 * Contains device related methods to obtain useful information
 * @author AraDevs
 */
class DeviceInfoHelper {
    /***
     * Returns true if the current device is flagged as non smartphone
     * @param context Represents the current app context
     */
    fun isTablet(context: Context): Boolean {
        return ((context.resources.configuration.screenLayout
                and Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE)
    }
}