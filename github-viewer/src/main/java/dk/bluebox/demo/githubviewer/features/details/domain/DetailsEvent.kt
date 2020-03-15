package dk.bluebox.demo.githubviewer.features.details.domain

import dk.bluebox.demo.githubviewer.common.domain.models.Repository

sealed class DetailsEvent {
    data class Load(val id: Long) : DetailsEvent()
    data class ToggleBookmark(val target: Repository) : DetailsEvent()
}
