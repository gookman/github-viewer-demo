package dk.bluebox.demo.githubviewer.common.ui.core

interface Cleaner<T> {
    fun add(item: T)
    fun clean()
}
