package dk.bluebox.demo.githubviewer.domain.details

import dk.bluebox.demo.githubviewer.data.GitHubRepository
import dk.bluebox.demo.githubviewer.domain.models.Repository
import io.reactivex.Observable

class DetailsInteractorImpl(
    private val repository: GitHubRepository
) : DetailsInteractor {

    override fun createDetailsStateObservable(eventsObservable: Observable<DetailsEvent>): Observable<DetailsState> {
        val toggleBookmarkObservable = eventsObservable
            .filter { it is DetailsEvent.ToggleBookmark }
            .switchMap { createToggleBookmarkObservable((it as DetailsEvent.ToggleBookmark).target) }

        val loadObservable = eventsObservable
            .filter { it is DetailsEvent.Load }
            .switchMap { createLoadObservable((it as DetailsEvent.Load).id) }

        return Observable.merge(toggleBookmarkObservable, loadObservable)
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

    private fun createToggleBookmarkObservable(item: Repository): Observable<DetailsState> {
        return repository.updateRepository(item.copy(bookmarked = item.bookmarked.not()))
            .andThen(createLoadObservable(item.id))
    }
}