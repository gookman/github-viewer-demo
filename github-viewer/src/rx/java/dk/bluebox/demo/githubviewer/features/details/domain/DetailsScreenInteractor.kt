package dk.bluebox.demo.githubviewer.features.details.domain

import dk.bluebox.demo.githubviewer.common.domain.AppEvent
import dk.bluebox.demo.githubviewer.common.domain.AppState
import dk.bluebox.demo.githubviewer.common.domain.ScreenInteractor
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class DetailsScreenInteractor(
    private val wrappedInteractor: DetailsInteractor
) : ScreenInteractor {

    private val subject = PublishSubject.create<DetailsEvent>()

    override fun createStateObservable(): Observable<AppState> {
        return wrappedInteractor
            .createDetailsStateObservable(subject)
            .map { AppState.Details(it) }
    }

    override fun sendEvent(event: AppEvent) {
        if (event is AppEvent.Details) {
            subject.onNext(event.value)
        }
    }
}