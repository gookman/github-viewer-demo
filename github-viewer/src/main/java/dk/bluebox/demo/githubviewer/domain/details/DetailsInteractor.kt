package dk.bluebox.demo.githubviewer.domain.details

import io.reactivex.Observable

interface DetailsInteractor {
    fun createDetailsStateObservable(eventsObservable: Observable<DetailsEvent>): Observable<DetailsState>
}