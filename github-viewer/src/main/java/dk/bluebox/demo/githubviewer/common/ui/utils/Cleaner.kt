package dk.bluebox.demo.githubviewer.common.ui.utils

interface Cleaner<T> {
    fun add(item: T)
    fun clean()
}
