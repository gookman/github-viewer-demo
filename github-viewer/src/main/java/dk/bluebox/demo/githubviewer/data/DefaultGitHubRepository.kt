package dk.bluebox.demo.githubviewer.data

import dk.bluebox.demo.githubviewer.domain.models.PullRequest
import dk.bluebox.demo.githubviewer.domain.models.Repository
import dk.bluebox.demo.githubviewer.network.GitHubApi
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class DefaultGitHubRepository(private val api: GitHubApi) : GitHubRepository {
    private val reposCache = LinkedHashMap<Long, Repository>()
    private val pullRequestsCache = HashMap<Int, List<PullRequest>>()

    override fun getRepositories(): Single<List<Repository>> {
        val cacheObservable = createRepositoriesCacheObservable()
        val networkObservable = createRepositoriesNetworkObservable()

        return Observable.concat(cacheObservable, networkObservable).firstOrError()
    }

    override fun getRepository(id: Long): Single<Repository> {
        return Single.create { emitter ->
            reposCache[id]?.let {
                emitter.onSuccess(it)
            } ?: emitter.onError(Throwable("Cannot find repo $id"))
        }
    }

    override fun updateRepository(repository: Repository): Completable {
        return Completable.create { emitter ->
            reposCache[repository.id] = repository
            emitter.onComplete()
        }
    }

    override fun getPullRequests(owner: String, repositoryName: String): Single<List<PullRequest>> {
        val key = getPullRequestsKey(owner, repositoryName)
        val cacheObservable = createPullRequestsCacheObservable(key)
        val networkObservable = createPullRequestsNetworkObservable(owner, repositoryName)

        return Observable.concat(cacheObservable, networkObservable).firstOrError()
    }

    private fun createRepositoriesCacheObservable(): Observable<List<Repository>> {
        return Observable.create { emitter ->
            if (reposCache.isNotEmpty()) {
                emitter.onNext(reposCache.values.toList())
            }

            emitter.onComplete()
        }
    }

    private fun createRepositoriesNetworkObservable(): Observable<List<Repository>> {
        return api.getRepositories()
            .toObservable()
            .doOnNext { repos ->
                reposCache.clear()
                repos.forEach {
                    reposCache[it.id] = it
                }
            }
    }

    private fun createPullRequestsCacheObservable(key: Int): Observable<List<PullRequest>> {
        return Observable.create { emitter ->
            pullRequestsCache[key]?.let {
                emitter.onNext(it)
            }

            emitter.onComplete()
        }
    }

    private fun createPullRequestsNetworkObservable(owner: String, repositoryName: String): Observable<List<PullRequest>> {
        return api.getPullRequests(owner, repositoryName)
            .toObservable()
            .doOnNext { pullRequests ->
                val key = getPullRequestsKey(owner, repositoryName)
                pullRequestsCache[key] = pullRequests
            }
    }

    private fun getPullRequestsKey(owner: String, repositoryName: String): Int {
        return owner.hashCode() + repositoryName.hashCode()
    }
}