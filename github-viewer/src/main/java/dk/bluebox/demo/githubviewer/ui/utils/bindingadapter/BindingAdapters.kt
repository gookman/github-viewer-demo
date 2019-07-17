package dk.bluebox.demo.githubviewer.ui.utils.bindingadapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("items")
fun setItems(recyclerView: RecyclerView, items: List<Nothing>?) {
    val adapter = recyclerView.adapter
    if (adapter is SingleTypeDataBindingAdapter<*>) {
        items?.let {
            adapter.setItems(it)
        }
    }
}