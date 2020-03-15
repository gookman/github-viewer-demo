package dk.bluebox.demo.githubviewer.common.network

import dk.bluebox.demo.githubviewer.common.domain.models.PullRequest
import dk.bluebox.demo.githubviewer.common.domain.models.Repository
import kotlinx.coroutines.flow.Flow

interface GitHubApi {
    fun getRepositories(): Flow<List<Repository>>
    fun getPullRequests(owner: String, repositoryName: String): Flow<List<PullRequest>>
}