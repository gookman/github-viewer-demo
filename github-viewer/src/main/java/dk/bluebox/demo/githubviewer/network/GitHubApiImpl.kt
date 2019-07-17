package dk.bluebox.demo.githubviewer.network

import dk.bluebox.demo.githubviewer.domain.models.PullRequest
import dk.bluebox.demo.githubviewer.domain.models.Repository
import dk.bluebox.demo.githubviewer.network.json.LocalDateTimeAdapter
import io.reactivex.Single

class GitHubApiImpl(serviceFactory: ServiceFactory) : GitHubApi {
        private val service = serviceFactory.createService(GitHubService::class.java, emptyList(), getJsonAdapters())

    override fun getRepositories(): Single<List<Repository>> {
        return service.getRepositories().map {
            it.toRepositoryList()
        }
    }

    override fun getPullRequests(owner: String, repositoryName: String): Single<List<PullRequest>> {
        return service.getPullRequests(owner, repositoryName).map { entities ->
            entities.slice(0 until MAX_RESULTS).map { it.toPullRequest() }
        }
    }

    private fun getJsonAdapters(): List<Any> {
        return listOf(LocalDateTimeAdapter())
    }

    companion object {
        private const val MAX_RESULTS = 10
    }
}