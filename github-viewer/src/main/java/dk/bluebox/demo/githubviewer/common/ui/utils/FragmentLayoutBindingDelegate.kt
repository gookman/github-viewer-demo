package dk.bluebox.demo.githubviewer.common.ui.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class FragmentLayoutBindingDelegate<T : ViewDataBinding>(private val layoutId: Int) : ReadOnlyProperty<Fragment, T> {

    private lateinit var binding: T

    fun inflate(layoutInflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(layoutInflater, layoutId, container, false)
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        if (!::binding.isInitialized) {
            throw IllegalStateException("The layout $layoutId has not been inflated")
        }

        return binding
    }
}

fun <T : ViewDataBinding> fragmentLayoutBindingInflater(layoutId: Int): FragmentLayoutBindingDelegate<T> {
    return FragmentLayoutBindingDelegate(layoutId)
}
