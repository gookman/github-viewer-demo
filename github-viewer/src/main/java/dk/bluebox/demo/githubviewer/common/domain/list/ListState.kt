package dk.bluebox.demo.githubviewer.common.domain.list

import dk.bluebox.demo.githubviewer.common.domain.models.Repository

sealed class ListState {
    object Loading : ListState()
    data class Error(val throwable: Throwable) : ListState()
    data class Success(val repositories: List<Repository>) : ListState()
}