package dk.bluebox.demo.githubviewer.common.network

import dk.bluebox.demo.githubviewer.common.domain.models.PullRequest
import dk.bluebox.demo.githubviewer.common.domain.models.Repository
import dk.bluebox.demo.githubviewer.common.network.json.LocalDateTimeAdapter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GitHubApiImpl(serviceFactory: ServiceFactory) :
    GitHubApi {
    private val service = serviceFactory.createService(GitHubService::class.java, emptyList(), getJsonAdapters())

    override fun getRepositories(): Flow<List<Repository>> {
        return flow {
            emit(service.getRepositories().toRepositoryList())
        }
    }

    override fun getPullRequests(owner: String, repositoryName: String): Flow<List<PullRequest>> {
        return flow {
            emit(service.getPullRequests(owner, repositoryName)
                .take(MAX_RESULTS)
                .map { it.toPullRequest() })
        }
    }

    private fun getJsonAdapters(): List<Any> {
        return listOf(LocalDateTimeAdapter())
    }

    companion object {
        private const val MAX_RESULTS = 10
    }
}