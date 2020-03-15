package dk.bluebox.demo.githubviewer.common.ui.bindingadapter

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
