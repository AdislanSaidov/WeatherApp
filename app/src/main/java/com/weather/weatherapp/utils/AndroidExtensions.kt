package com.weather.weatherapp.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

fun Activity.hideSoftKeyboard() {
    val currentFocus = this.currentFocus
    if (currentFocus != null) {
        val iim = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        iim.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }
}
