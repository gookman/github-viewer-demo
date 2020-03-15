package dk.bluebox.demo.githubviewer.common.data

import dk.bluebox.demo.githubviewer.common.domain.models.PullRequest
import dk.bluebox.demo.githubviewer.common.domain.models.Repository
import dk.bluebox.demo.githubviewer.common.concatFlows
import dk.bluebox.demo.githubviewer.common.firstFlow
import dk.bluebox.demo.githubviewer.common.network.GitHubApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

class DefaultGitHubRepository(private val api: GitHubApi) : GitHubRepository {
    private val reposCache = LinkedHashMap<Long, Repository>()
    private val pullRequestsCache = HashMap<Int, List<PullRequest>>()

    @FlowPreview
    override fun getRepositories(): Flow<List<Repository>> {
        val cacheFlow = createRepositoriesCacheFlow()
        val networkFlow = createRepositoriesNetworkFlow()

        return concatFlows(
            cacheFlow,
            networkFlow
        ).firstFlow()
    }

    override fun getRepository(id: Long): Flow<Repository> {
        return flow {
            reposCache[id]?.let {
                emit(it)
            } ?: throw Throwable("Cannot find repo $id")
        }
    }

    override fun updateRepository(repository: Repository): Flow<Unit> {
        return flow {
            reposCache[repository.id] = repository
            emit(Unit)
        }
    }

    @FlowPreview
    override fun getPullRequests(owner: String, repositoryName: String): Flow<List<PullRequest>> {
        val key = getPullRequestsKey(owner, repositoryName)
        val cacheFlow = createPullRequestsCacheFlow(key)
        val networkFlow = createPullRequestsNetworkFlow(owner, repositoryName)

        return concatFlows(
            cacheFlow,
            networkFlow
        ).firstFlow()
    }

    private fun createRepositoriesCacheFlow(): Flow<List<Repository>> {
        return flow {
            if (reposCache.isNotEmpty()) {
                emit(reposCache.values.toList())
            }
        }
    }

    private fun createRepositoriesNetworkFlow(): Flow<List<Repository>> {
        return api.getRepositories().onEach { repos ->
            reposCache.clear()
            repos.forEach {
                reposCache[it.id] = it
            }
        }
    }

    private fun createPullRequestsCacheFlow(key: Int): Flow<List<PullRequest>> {
        return flow {
            pullRequestsCache[key]?.let {
                emit(it)
            }
        }
    }

    private fun createPullRequestsNetworkFlow(owner: String, repositoryName: String): Flow<List<PullRequest>> {
        return api.getPullRequests(owner, repositoryName)
            .onEach { pullRequests ->
                val key = getPullRequestsKey(owner, repositoryName)
                pullRequestsCache[key] = pullRequests
            }
    }

    private fun getPullRequestsKey(owner: String, repositoryName: String): Int {
        return owner.hashCode() + repositoryName.hashCode()
    }
}