package dk.bluebox.demo.githubviewer.common.ui.utils

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class PropertyBindingDelegate<T>(private val propertyId: Int, defaultValue: T) : ReadWriteProperty<BaseViewModel, T>  {
    private var backingValue = defaultValue

    override fun getValue(thisRef: BaseViewModel, property: KProperty<*>): T {
        return backingValue
    }

    override fun setValue(thisRef: BaseViewModel, property: KProperty<*>, value: T) {
        backingValue = value
        thisRef.notifyPropertyChanged(propertyId)
    }
}

fun <T> propertyBinding(propertyId: Int, defaultValue: T): PropertyBindingDelegate<T> {
    return PropertyBindingDelegate(propertyId, defaultValue)
}