package dk.bluebox.demo.githubviewer.common.ui.bindingadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

class DataBindingInfo(
    val layoutId: Int,
    val variableId: Int
) {

    fun createViewHolder(parent: ViewGroup): DataBindingViewHolder {
        val binding: ViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                layoutId, parent, false)

        return DataBindingViewHolder(binding, variableId)
    }
}
