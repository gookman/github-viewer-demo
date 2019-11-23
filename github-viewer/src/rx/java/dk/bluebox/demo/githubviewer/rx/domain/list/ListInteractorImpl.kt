package dk.bluebox.demo.githubviewer.rx.domain.list

import dk.bluebox.demo.githubviewer.common.domain.list.ListEvent
import dk.bluebox.demo.githubviewer.common.domain.list.ListState
import dk.bluebox.demo.githubviewer.rx.data.GitHubRepository
import dk.bluebox.demo.githubviewer.common.navigation.Router
import io.reactivex.Observable

class ListInteractorImpl(
    private val router: Router,
    private val repository: GitHubRepository
) : ListInteractor {

    override fun createListStateObservable(eventsObservable: Observable<ListEvent>): Observable<ListState> {
        val loadFilter = eventsObservable
            .filter { it is ListEvent.Load }
            .switchMap { createLoadObservable() }

        val navigateFilter = eventsObservable
            .filter { it is ListEvent.Navigate }
            .doOnNext { router.goToDetails((it as ListEvent.Navigate).id) }
            .map { ListState.Loading }

        return Observable
            .merge(loadFilter, navigateFilter)
            .onErrorReturn { ListState.Error(it) }
    }

    private fun createLoadObservable(): Observable<ListState> {
        return repository
            .getRepositories()
            .toObservable()
            .map<ListState> { ListState.Success(it) }
            .startWith(ListState.Loading)
    }
}