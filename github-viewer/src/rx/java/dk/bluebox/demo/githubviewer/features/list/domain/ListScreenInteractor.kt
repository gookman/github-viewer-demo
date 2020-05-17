package dk.bluebox.demo.githubviewer.features.list.domain

import dk.bluebox.demo.githubviewer.common.domain.AppEvent
import dk.bluebox.demo.githubviewer.common.domain.AppState
import dk.bluebox.demo.githubviewer.common.domain.ScreenInteractor
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class ListScreenInteractor(
    private val wrappedInteractor: ListInteractor
) : ScreenInteractor {

    private val subject = PublishSubject.create<ListEvent>()

    override fun createStateObservable(): Observable<AppState> {
        return wrappedInteractor
            .createListStateObservable(subject)
            .map {
                AppState.List(it)
            }
    }

    override fun sendEvent(event: AppEvent) {
        if (event is AppEvent.List) {
            subject.onNext(event.value)
        }
    }
}