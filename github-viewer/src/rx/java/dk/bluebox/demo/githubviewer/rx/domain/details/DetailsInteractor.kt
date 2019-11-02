package dk.bluebox.demo.githubviewer.rx.domain.details

import dk.bluebox.demo.githubviewer.common.domain.details.DetailsEvent
import dk.bluebox.demo.githubviewer.common.domain.details.DetailsState
import io.reactivex.Observable

interface DetailsInteractor {
    fun createDetailsStateObservable(eventsObservable: Observable<DetailsEvent>): Observable<DetailsState>
}