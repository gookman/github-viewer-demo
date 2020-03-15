package dk.bluebox.demo.githubviewer.features.list.domain

import io.reactivex.Observable

interface ListInteractor {
    fun createListStateObservable(eventsObservable: Observable<ListEvent>): Observable<ListState>
}