package dk.bluebox.demo.githubviewer.flow.data

import dk.bluebox.demo.githubviewer.common.domain.models.PullRequest
import dk.bluebox.demo.githubviewer.common.domain.models.Repository
import kotlinx.coroutines.flow.Flow

interface GitHubRepository {
    fun getRepositories(): Flow<List<Repository>>
    fun getRepository(id: Long): Flow<Repository>
    fun updateRepository(repository: Repository): Flow<Unit>
    fun getPullRequests(owner: String, repositoryName: String): Flow<List<PullRequest>>
}