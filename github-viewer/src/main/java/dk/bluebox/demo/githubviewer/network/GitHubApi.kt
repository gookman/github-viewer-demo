package dk.bluebox.demo.githubviewer.network

import dk.bluebox.demo.githubviewer.domain.models.PullRequest
import dk.bluebox.demo.githubviewer.domain.models.Repository
import io.reactivex.Single

interface GitHubApi {
    fun getRepositories(): Single<List<Repository>>
    fun getPullRequests(owner: String, repositoryName: String): Single<List<PullRequest>>
}