package dk.bluebox.demo.githubviewer.common.domain

import io.reactivex.Observable

interface ScreenInteractor {
    fun createStateObservable(): Observable<AppState>
    fun sendEvent(event: AppEvent)
}