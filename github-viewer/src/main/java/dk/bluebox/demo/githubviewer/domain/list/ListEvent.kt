package dk.bluebox.demo.githubviewer.domain.list

sealed class ListEvent {
    object Load : ListEvent()
    data class Navigate(val id: Long) : ListEvent()
}