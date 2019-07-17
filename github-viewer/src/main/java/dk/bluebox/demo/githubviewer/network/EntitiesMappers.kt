package dk.bluebox.demo.githubviewer.network

import dk.bluebox.demo.githubviewer.domain.models.PullRequest
import dk.bluebox.demo.githubviewer.domain.models.Repository
import dk.bluebox.demo.githubviewer.network.entities.PullRequestEntity
import dk.bluebox.demo.githubviewer.network.entities.RepositoryEntity
import dk.bluebox.demo.githubviewer.network.entities.SearchResponseEntity
import org.threeten.bp.LocalDateTime

fun SearchResponseEntity.toRepositoryList(): List<Repository> {
    return items.map { it.toGitHubRepo() }
}

fun RepositoryEntity.toGitHubRepo(): Repository {
    return object : Repository {
        override val id = this@toGitHubRepo.id
        override val name = this@toGitHubRepo.name
        override val ownerName = this@toGitHubRepo.owner.name
        override val starsCount = this@toGitHubRepo.starsCount
        override val watchersCount = this@toGitHubRepo.watchersCount
        override val openIssuesCount = this@toGitHubRepo.openIssuesCount
        override val forksCount = this@toGitHubRepo.forksCount
        override val description = this@toGitHubRepo.description
        override val lastUpdate = this@toGitHubRepo.lastUpdate
        override val pullsUrl = this@toGitHubRepo.pullsUrl
        override val language = this@toGitHubRepo.language ?: ""
    }
}

fun PullRequestEntity.toPullRequest(): PullRequest {
    return object : PullRequest {
        override val id = this@toPullRequest.id
        override val title: String = this@toPullRequest.title
        override val number = this@toPullRequest.number
        override val state = this@toPullRequest.state.toState()
        override val ownerName = this@toPullRequest.owner.name
        override val lastUpdate = this@toPullRequest.lastUpdate

    }
}

private fun String.toState(): PullRequest.State {
    return when (this) {
        "open" -> PullRequest.State.OPEN
        "closed" -> PullRequest.State.CLOSED
        else -> PullRequest.State.OPEN
    }
}