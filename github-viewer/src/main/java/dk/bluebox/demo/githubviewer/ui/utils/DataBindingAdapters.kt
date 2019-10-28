package dk.bluebox.demo.githubviewer.ui.utils

import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter

@BindingAdapter("visible")
fun setViewVisible(view: View, visible: Boolean) {
    if (visible) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("android:src")
fun setImageByResId(imageView: ImageView, @DrawableRes resId: Int) {
    imageView.setImageResource(resId)
}