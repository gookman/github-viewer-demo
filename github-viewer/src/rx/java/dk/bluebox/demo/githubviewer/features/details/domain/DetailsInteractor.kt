package dk.bluebox.demo.githubviewer.features.details.domain

import io.reactivex.Observable

interface DetailsInteractor {
    fun createDetailsStateObservable(eventsObservable: Observable<DetailsEvent>): Observable<DetailsState>
}