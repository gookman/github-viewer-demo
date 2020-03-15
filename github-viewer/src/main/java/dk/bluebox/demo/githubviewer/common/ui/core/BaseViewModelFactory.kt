package dk.bluebox.demo.githubviewer.common.ui.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BaseViewModelFactory<T : ViewModel>(
    private val createCallback: () -> T
) : ViewModelProvider.Factory {

    override fun <V : ViewModel> create(modelClass: Class<V>): V {
        return cast(createCallback(), modelClass)
    }

    @Suppress("UNCHECKED_CAST")
    private fun <V> cast(value: Any, modelClass: Class<V>): V {
        return if (modelClass.isAssignableFrom(value::class.java)) {
            value as V
        } else {
            throw ClassCastException("${value::class.java.simpleName} cannot " +
                    "be cast to ${modelClass.simpleName}")
        }
    }
}
