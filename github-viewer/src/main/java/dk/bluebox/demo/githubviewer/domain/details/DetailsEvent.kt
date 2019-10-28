package dk.bluebox.demo.githubviewer.domain.details

import dk.bluebox.demo.githubviewer.domain.models.Repository

sealed class DetailsEvent {
    data class Load(val id: Long) : DetailsEvent()
    data class ToggleBookmark(val target: Repository) : DetailsEvent()
}