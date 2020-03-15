package dk.bluebox.demo.githubviewer.features.list.domain

sealed class ListEvent {
    object Load : ListEvent()
    data class Navigate(val id: Long) : ListEvent()
}
