package dk.bluebox.demo.githubviewer.domain.details

import dk.bluebox.demo.githubviewer.domain.models.PullRequest
import dk.bluebox.demo.githubviewer.domain.models.Repository

sealed class DetailsState {
    object Loading : DetailsState()
    data class Error(val throwable: Throwable) : DetailsState()
    data class Success(val repository: Repository, val pullRequests: List<PullRequest>) : DetailsState()
}