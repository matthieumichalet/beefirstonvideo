package io.involvedapps.beefirstonvideo.utils.views

import android.content.Context
import android.content.res.Configuration
import android.view.View
import android.view.inputmethod.InputMethodManager

internal fun hideKeyboard(context: Context, view: View) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun getScreenOrientation(configuration: Configuration) : ScreenOrientation {
    val orientation = configuration.orientation

    return when (orientation) {
        Configuration.ORIENTATION_PORTRAIT -> {
            ScreenOrientation.Portrait
        }
        Configuration.ORIENTATION_LANDSCAPE -> {
            ScreenOrientation.Landscape
        }
        else -> {
            ScreenOrientation.Portrait
        }
    }
}

sealed interface ScreenOrientation {
    data object Portrait : ScreenOrientation
    data object Landscape : ScreenOrientation
}