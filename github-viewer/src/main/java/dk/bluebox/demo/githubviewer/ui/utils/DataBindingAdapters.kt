package dk.bluebox.demo.githubviewer.ui.utils

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("visible")
fun setViewVisible(view: View, visible: Boolean) {
    if (visible) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}