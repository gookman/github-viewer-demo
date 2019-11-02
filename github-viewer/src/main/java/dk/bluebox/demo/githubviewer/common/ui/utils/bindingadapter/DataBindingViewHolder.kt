package dk.bluebox.demo.githubviewer.common.ui.utils.bindingadapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class DataBindingViewHolder(val binding: ViewDataBinding, val variableId: Int) : RecyclerView.ViewHolder(binding.root)