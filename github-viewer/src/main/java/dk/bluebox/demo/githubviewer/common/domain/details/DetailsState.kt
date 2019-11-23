package dk.bluebox.demo.githubviewer.common.domain.details

import dk.bluebox.demo.githubviewer.common.domain.models.PullRequest
import dk.bluebox.demo.githubviewer.common.domain.models.Repository

sealed class DetailsState {
    object Loading : DetailsState()
    data class Error(val throwable: Throwable) : DetailsState()
    data class Success(val repository: Repository, val pullRequests: List<PullRequest>) : DetailsState()
}
