package dk.bluebox.demo.githubviewer.domain.list

import io.reactivex.Observable

interface ListInteractor {
    fun createListStateObservable(eventsObservable: Observable<ListEvent>): Observable<ListState>
}