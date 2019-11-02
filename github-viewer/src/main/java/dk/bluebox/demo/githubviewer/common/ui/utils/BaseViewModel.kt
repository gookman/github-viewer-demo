package dk.bluebox.demo.githubviewer.common.ui.utils

import androidx.annotation.CallSuper
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel(), Observable {

    protected abstract val cleaner: Cleaner<*>

    @Transient
    private var callbacks: PropertyChangeRegistry? = null

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        cleaner.clean()
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        synchronized(this) {
            if (callbacks == null) {
                return
            }
        }
        callbacks?.remove(callback)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        synchronized(this) {
            if (callbacks == null) {
                callbacks = PropertyChangeRegistry()
            }
        }
        callbacks?.add(callback)
    }

    fun notifyChange() {
        synchronized(this) {
            if (callbacks == null) {
                return
            }
        }
        callbacks?.notifyCallbacks(this, 0, null)
    }

    fun notifyPropertyChanged(fieldId: Int) {
        synchronized(this) {
            if (callbacks == null) {
                return
            }
        }
        callbacks?.notifyCallbacks(this, fieldId, null)
    }
}