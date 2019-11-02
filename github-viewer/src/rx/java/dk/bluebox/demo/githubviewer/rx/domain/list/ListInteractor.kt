package dk.bluebox.demo.githubviewer.rx.domain.list

import dk.bluebox.demo.githubviewer.common.domain.list.ListEvent
import dk.bluebox.demo.githubviewer.common.domain.list.ListState
import io.reactivex.Observable

interface ListInteractor {
    fun createListStateObservable(eventsObservable: Observable<ListEvent>): Observable<ListState>
}