package dk.bluebox.demo.githubviewer.common.ui.core.databinding.bindingadapter

abstract class SingleTypeDataBindingAdapter<T : DataBindingItem> : DataBindingAdapter() {

    private var items: ArrayList<T> = ArrayList()

    override fun getItem(position: Int): T {
        if (position >= items.size) {
            throw IndexOutOfBoundsException("Item not found at position $position")
        }

        return items[position]
    }

    override fun getItemCount() = items.size

    fun setItems(newItems: List<T>) {
        items = ArrayList(newItems)
        notifyDataSetChanged()
    }
}
