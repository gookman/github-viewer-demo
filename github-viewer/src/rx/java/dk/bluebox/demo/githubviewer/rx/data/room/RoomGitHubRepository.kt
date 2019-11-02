package dk.bluebox.demo.githubviewer.rx.data.room

import dk.bluebox.demo.githubviewer.common.data.room.RepositoryEntity
import dk.bluebox.demo.githubviewer.rx.data.DefaultGitHubRepository
import dk.bluebox.demo.githubviewer.rx.data.GitHubRepository
import dk.bluebox.demo.githubviewer.common.domain.models.PullRequest
import dk.bluebox.demo.githubviewer.common.domain.models.Repository
import dk.bluebox.demo.githubviewer.rx.SchedulersProvider
import io.reactivex.Completable
import io.reactivex.Single

class RoomGitHubRepository(
    private val defaultRepository: DefaultGitHubRepository,
    private val database: AppDatabase,
    private val schedulersProvider: SchedulersProvider
) : GitHubRepository {

    override fun getRepositories(): Single<List<Repository>> {
        return defaultRepository.getRepositories()
    }

    override fun getRepository(id: Long): Single<Repository> {
        return defaultRepository.getRepository(id)
            .flatMap { repository ->
                database.repositoryDao()
                    .findById(id)
                    .map { repository.copy(bookmarked = it.bookmarked) }
                    .onErrorReturnItem(repository)
                    .subscribeOn(schedulersProvider.io())
            }
    }

    override fun updateRepository(repository: Repository): Completable {
        return database.repositoryDao()
            .insert(
                RepositoryEntity(
                    repository.id,
                    repository.bookmarked
                )
            )
            .subscribeOn(schedulersProvider.io())
    }

    override fun getPullRequests(owner: String, repositoryName: String): Single<List<PullRequest>> {
        return defaultRepository.getPullRequests(owner, repositoryName)
    }
}