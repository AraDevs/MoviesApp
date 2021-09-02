package com.aradevs.moviesapp.helpers

import android.app.Dialog
import android.view.Window
import android.widget.Button
import android.widget.NumberPicker
import androidx.fragment.app.FragmentActivity
import com.aradevs.moviesapp.R

/***
 * Pagination helper
 * Contains a set of methods related to pagination process and widgets
 * @author AraDevs
 * @property activity Represents the parent activity of the request
 * @property maxPages Represents the maximum number of pages that the URL can handle
 * @property currentPage Represents the current page the user is in
 */
class PaginationHelper(private val activity: FragmentActivity, private val maxPages: Int, private val currentPage: Int) {

    /***
     * Inflates a dialog containing a NumberPicker to select a page number
     * @param onSuccess represents the function to be executed after the user input
     */
    fun showDialog(onSuccess : (input: Int) -> Unit) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.fragment_pagination)

        val pagePicker : NumberPicker = dialog.findViewById(R.id.testPicker)
        val selectPageBtn : Button = dialog.findViewById(R.id.selectPageBtn)
        val cancelPageBtn : Button = dialog.findViewById(R.id.cancelPageBtn)

        initializePicker(pagePicker)
        selectPageBtn.setOnClickListener {
            onSuccess(pagePicker.value)
            dialog.dismiss()
        }
        cancelPageBtn.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    /***
     * Initializes the page picker values
     * @param pagePicker Represents the page picker to be initialized
     */
    private fun initializePicker(pagePicker : NumberPicker){
        pagePicker.minValue = 1
        pagePicker.maxValue = maxPages
        pagePicker.value = currentPage
    }


}