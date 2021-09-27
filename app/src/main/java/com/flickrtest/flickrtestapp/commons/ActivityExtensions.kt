package com.flickrtest.flickrtestapp.commons

import android.app.Activity
import android.view.View
import com.flickrtest.flickrtestapp.R
import com.google.android.material.snackbar.Snackbar

internal fun Activity.showSnackbar(view: View, message: String, isError: Boolean = false) {
    val sb = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
    if (isError)
        sb.setBackgroundTint(loadColor(R.color.colorError))
            .setTextColor(loadColor(R.color.colorOnError))
            .show()
    else
        sb.setBackgroundTint(loadColor(R.color.colorSecondary))
            .setTextColor(loadColor(R.color.colorOnSecondary))
            .show()

}