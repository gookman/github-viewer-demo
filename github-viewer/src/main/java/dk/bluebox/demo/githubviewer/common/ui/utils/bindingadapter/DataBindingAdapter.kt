package dk.bluebox.demo.githubviewer.common.ui.utils.bindingadapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class DataBindingAdapter : RecyclerView.Adapter<DataBindingViewHolder>() {

    open val itemClickCallback: (position: Int, item: DataBindingItem) -> Unit = { _, _ -> }

    private val bindingInfoMap = HashMap<Int, DataBindingInfo>()

    abstract fun getItem(position: Int): DataBindingItem

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder {
        return bindingInfoMap[viewType]?.createViewHolder(parent)
            ?: throw IllegalStateException("Cannot create view holder for type $viewType")
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) {
        val binding = holder.binding
        val item = getItem(position)

        binding.setVariable(holder.variableId, item)
        binding.root.setOnClickListener { itemClickCallback(position, item) }

        if (binding.hasPendingBindings()) {
            binding.notifyChange()
            binding.executePendingBindings()
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)

        if (!bindingInfoMap.containsKey(item.dataBindingInfo.layoutId)) {
            bindingInfoMap[item.dataBindingInfo.layoutId] = item.dataBindingInfo
        }

        return item.dataBindingInfo.layoutId
    }
}
