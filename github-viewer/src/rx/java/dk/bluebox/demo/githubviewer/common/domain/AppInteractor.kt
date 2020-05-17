package dk.bluebox.demo.githubviewer.common.domain

import androidx.activity.OnBackPressedCallback
import dk.bluebox.demo.githubviewer.common.domain.models.Repository
import dk.bluebox.demo.githubviewer.common.navigation.Router
import io.reactivex.Observable

interface AppInteractor : Router {
    val appStateObservable: Observable<AppState>
    val onBackPressedCallback: OnBackPressedCallback

    fun goToList()

    fun toggleBookmark(target: Repository)

    fun retryLast()
}