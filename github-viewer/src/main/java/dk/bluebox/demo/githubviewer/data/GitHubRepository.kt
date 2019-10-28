package dk.bluebox.demo.githubviewer.data

import dk.bluebox.demo.githubviewer.domain.models.PullRequest
import dk.bluebox.demo.githubviewer.domain.models.Repository
import io.reactivex.Completable
import io.reactivex.Single

interface GitHubRepository {
    fun getRepositories(): Single<List<Repository>>
    fun getRepository(id: Long): Single<Repository>
    fun updateRepository(repository: Repository): Completable
    fun getPullRequests(owner: String, repositoryName: String): Single<List<PullRequest>>
}