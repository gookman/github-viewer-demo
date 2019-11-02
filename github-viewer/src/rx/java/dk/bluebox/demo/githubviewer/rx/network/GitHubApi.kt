package dk.bluebox.demo.githubviewer.rx.network

import dk.bluebox.demo.githubviewer.common.domain.models.PullRequest
import dk.bluebox.demo.githubviewer.common.domain.models.Repository
import io.reactivex.Single

interface GitHubApi {
    fun getRepositories(): Single<List<Repository>>
    fun getPullRequests(owner: String, repositoryName: String): Single<List<PullRequest>>
}