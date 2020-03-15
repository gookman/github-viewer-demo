package dk.bluebox.demo.githubviewer.features.list.domain

import dk.bluebox.demo.githubviewer.common.domain.models.Repository

sealed class ListState {
    object Loading : ListState()
    data class Error(val throwable: Throwable) : ListState()
    data class Success(val repositories: List<Repository>) : ListState()
}
