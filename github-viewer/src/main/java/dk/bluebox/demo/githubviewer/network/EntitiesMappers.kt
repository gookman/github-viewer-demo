package dk.bluebox.demo.githubviewer.network

import dk.bluebox.demo.githubviewer.domain.models.PullRequest
import dk.bluebox.demo.githubviewer.domain.models.Repository
import dk.bluebox.demo.githubviewer.network.entities.PullRequestEntity
import dk.bluebox.demo.githubviewer.network.entities.RepositoryEntity
import dk.bluebox.demo.githubviewer.network.entities.SearchResponseEntity

fun SearchResponseEntity.toRepositoryList(): List<Repository> {
    return items.map { it.toGitHubRepo() }
}

fun RepositoryEntity.toGitHubRepo(): Repository {
    return Repository(
        id = this@toGitHubRepo.id,
        name = this@toGitHubRepo.name,
        ownerName = this@toGitHubRepo.owner.name,
        starsCount = this@toGitHubRepo.starsCount,
        watchersCount = this@toGitHubRepo.watchersCount,
        openIssuesCount = this@toGitHubRepo.openIssuesCount,
        forksCount = this@toGitHubRepo.forksCount,
        description = this@toGitHubRepo.description,
        lastUpdate = this@toGitHubRepo.lastUpdate,
        pullsUrl = this@toGitHubRepo.pullsUrl,
        language = this@toGitHubRepo.language ?: "",
        bookmarked = false
    )
}

fun PullRequestEntity.toPullRequest(): PullRequest {
    return PullRequest(
        id = this@toPullRequest.id,
        title = this@toPullRequest.title,
        number = this@toPullRequest.number,
        state = this@toPullRequest.state.toState(),
        ownerName = this@toPullRequest.owner.name,
        lastUpdate = this@toPullRequest.lastUpdate
    )
}

private fun String.toState(): PullRequest.State {
    return when (this) {
        "open" -> PullRequest.State.OPEN
        "closed" -> PullRequest.State.CLOSED
        else -> PullRequest.State.OPEN
    }
}