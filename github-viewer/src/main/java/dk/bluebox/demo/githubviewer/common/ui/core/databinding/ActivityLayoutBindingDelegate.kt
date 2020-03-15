package dk.bluebox.demo.githubviewer.common.ui.core.databinding

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ActivityLayoutBindingDelegate<T : ViewDataBinding>(
    private val layoutId: Int
) : ReadOnlyProperty<AppCompatActivity, T> {

    private lateinit var binding: T

    override fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): T {
        if (!::binding.isInitialized) {
            binding = DataBindingUtil.setContentView(thisRef, layoutId)
        }

        return binding
    }
}

fun <T : ViewDataBinding> activityLayoutBinding(layoutId: Int): ActivityLayoutBindingDelegate<T> {
    return ActivityLayoutBindingDelegate(
        layoutId
    )
}
