package dk.bluebox.demo.githubviewer.common.data.room

import dk.bluebox.demo.githubviewer.common.data.DefaultGitHubRepository
import dk.bluebox.demo.githubviewer.common.data.GitHubRepository
import dk.bluebox.demo.githubviewer.common.domain.models.PullRequest
import dk.bluebox.demo.githubviewer.common.domain.models.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

@ExperimentalCoroutinesApi
@FlowPreview
class RoomGitHubRepository(
    private val defaultRepository: DefaultGitHubRepository,
    private val database: AppDatabase
) : GitHubRepository {

    override fun getRepositories(): Flow<List<Repository>> {
        return defaultRepository.getRepositories()
    }

    override fun getRepository(id: Long): Flow<Repository> {
        return defaultRepository.getRepository(id)
            .flatMapConcat { repository ->
                flow { emit(database.repositoryDao().findById(id)) }
                    .map { repository.copy(bookmarked = it.bookmarked) }
                    .catch { emit(repository) }
                    .flowOn(Dispatchers.IO)
            }
    }

    override fun updateRepository(repository: Repository): Flow<Unit> {
        return flow {
                database.repositoryDao()
                    .insert(
                        RepositoryEntity(
                            repository.id,
                            repository.bookmarked
                        )
                    )
                emit(Unit)
            }
            .flowOn(Dispatchers.IO)
    }

    override fun getPullRequests(owner: String, repositoryName: String): Flow<List<PullRequest>> {
        return defaultRepository.getPullRequests(owner, repositoryName)
    }
}