package dk.bluebox.demo.githubviewer.domain.details

sealed class DetailsEvent {
    data class Load(val id: Long) : DetailsEvent()
}