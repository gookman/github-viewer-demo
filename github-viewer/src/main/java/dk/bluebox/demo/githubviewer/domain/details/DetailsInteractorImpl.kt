package dk.bluebox.demo.githubviewer.domain.details

import dk.bluebox.demo.githubviewer.domain.GitHubRepository
import io.reactivex.Observable

class DetailsInteractorImpl(
    private val repository: GitHubRepository
) : DetailsInteractor {
    override fun createDetailsStateObservable(eventsObservable: Observable<DetailsEvent>): Observable<DetailsState> {
        return eventsObservable
            .filter { it is DetailsEvent.Load }
            .switchMap { createLoadObservable((it as DetailsEvent.Load).id) }
    }

    private fun createLoadObservable(id: Long): Observable<DetailsState> {
        return repository
            .getRepository(id)
            .toObservable()
            .switchMap { repo ->
                repository.getPullRequests(repo.ownerName, repo.name)
                    .toObservable()
                    .map { Pair(repo, it) }
            }
            .map<DetailsState> {
                DetailsState.Success(it.first, it.second)
            }
            .startWith(DetailsState.Loading)
    }
}