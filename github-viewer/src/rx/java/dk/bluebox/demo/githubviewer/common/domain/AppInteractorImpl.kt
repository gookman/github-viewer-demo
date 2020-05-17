package dk.bluebox.demo.githubviewer.common.domain

import androidx.activity.OnBackPressedCallback
import dk.bluebox.demo.githubviewer.common.domain.models.Repository
import dk.bluebox.demo.githubviewer.common.rx.SchedulersProvider
import dk.bluebox.demo.githubviewer.features.details.domain.DetailsEvent
import dk.bluebox.demo.githubviewer.features.details.domain.DetailsScreenInteractor
import dk.bluebox.demo.githubviewer.features.details.domain.DetailsState
import dk.bluebox.demo.githubviewer.features.list.domain.ListEvent
import dk.bluebox.demo.githubviewer.features.list.domain.ListScreenInteractor
import dk.bluebox.demo.githubviewer.features.list.domain.ListState
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.ReplaySubject
import kotlin.reflect.KClass

class AppInteractorImpl(
    initialState: AppState,
    private val screenInteractorFactory: ScreenInteractorFactory,
    private val schedulersProvider: SchedulersProvider
) : AppInteractor {

    override val appStateObservable: Observable<AppState>
        get() = appStateReplaySubject

    override val onBackPressedCallback = createOnBackPressedCallback()

    private val appStateReplaySubject = ReplaySubject.createWithSize<AppState>(1)

    private var currentScreenInteractor = initialState.createScreenInteractor()

    private var currentDisposable: Disposable

    private lateinit var lastEvent: AppEvent

    init {
        currentDisposable = subscribeToCurrentScreenInteractor()
        sendEvent(initialState.toEvent())
    }

    override fun goToDetails(id: Long) {
        setupNewScreen(DetailsScreenInteractor::class)

        sendEvent(AppEvent.Details(DetailsEvent.Load(id)))
        onBackPressedCallback.isEnabled = true
    }

    override fun goToList() {
        setupNewScreen(ListScreenInteractor::class)

        sendEvent(AppEvent.List(ListEvent.Load))
        onBackPressedCallback.isEnabled = false
    }

    override fun toggleBookmark(target: Repository) {
        sendEvent(AppEvent.Details(DetailsEvent.ToggleBookmark(target)))
    }

    override fun retryLast() {
        currentDisposable.dispose()
        currentDisposable = subscribeToCurrentScreenInteractor()
        sendEvent(lastEvent)
    }

    private fun <T : ScreenInteractor> setupNewScreen(clazz: KClass<T>) {
        currentScreenInteractor = screenInteractorFactory.createInteractor(clazz)
        currentDisposable.dispose()
        currentDisposable = subscribeToCurrentScreenInteractor()
    }

    private fun handleState(state: AppState) {
        val mappedState = when {
            state is AppState.List && state.value is ListState.Error -> AppState.Error(state.value.throwable)
            state is AppState.Details && state.value is DetailsState.Error -> AppState.Error(state.value.throwable)
            else -> state
        }

        appStateReplaySubject.onNext(mappedState)
    }

    private fun handleError(throwable: Throwable) {
        appStateReplaySubject.onNext(AppState.Error(throwable))
    }

    private fun subscribeToCurrentScreenInteractor(): Disposable {
        return currentScreenInteractor
            .createStateObservable()
            .observeOn(schedulersProvider.main())
            .subscribe(::handleState, ::handleError)
    }

    private fun AppState.createScreenInteractor(): ScreenInteractor {
        return when (this) {
            is AppState.List -> screenInteractorFactory.createInteractor(ListScreenInteractor::class)
            is AppState.Details -> screenInteractorFactory.createInteractor(DetailsScreenInteractor::class)
            is AppState.Error -> error("Cannot have an interactor for an error state")
        }
    }

    private fun sendEvent(event: AppEvent) {
        lastEvent = event
        currentScreenInteractor.sendEvent(event)
    }

    private fun AppState.toEvent(): AppEvent {
        return when (this) {
            is AppState.List -> AppEvent.List(value.toEvent())
            is AppState.Details -> AppEvent.Details(value.toEvent())
            is AppState.Error -> AppEvent.Error
        }
    }

    private fun ListState.toEvent(): ListEvent {
        return when (this) {
            ListState.Loading -> ListEvent.Load
            else -> error("Unsupported state conversion")
        }
    }

    private fun DetailsState.toEvent(): DetailsEvent {
        return when (this) {
            is DetailsState.Success -> DetailsEvent.Load(this.repository.id)
            else -> error("Unsupported state conversion")
        }
    }

    private fun createOnBackPressedCallback(): OnBackPressedCallback {
        return object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (currentScreenInteractor is DetailsScreenInteractor) {
                    goToList()
                }
            }
        }
    }
}