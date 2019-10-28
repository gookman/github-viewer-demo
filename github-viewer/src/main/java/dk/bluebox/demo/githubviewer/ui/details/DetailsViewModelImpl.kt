package dk.bluebox.demo.githubviewer.ui.details

import android.content.Context
import android.util.Log
import dk.bluebox.demo.githubviewer.BR
import dk.bluebox.demo.githubviewer.R
import dk.bluebox.demo.githubviewer.domain.details.DetailsEvent
import dk.bluebox.demo.githubviewer.domain.details.DetailsInteractor
import dk.bluebox.demo.githubviewer.domain.details.DetailsState
import dk.bluebox.demo.githubviewer.domain.models.PullRequest
import dk.bluebox.demo.githubviewer.domain.models.Repository
import dk.bluebox.demo.githubviewer.rx.SchedulersProvider
import dk.bluebox.demo.githubviewer.ui.utils.propertyBinding
import io.reactivex.subjects.PublishSubject

class DetailsViewModelImpl(
    private val context: Context,
    interactor: DetailsInteractor,
    schedulersProvider: SchedulersProvider
) : DetailsViewModel() {
    override val title = context.getString(R.string.details_title)

    override var loadingVisible by propertyBinding(BR.loadingVisible, true)

    override var contentsVisible by propertyBinding(BR.contentsVisible, false)

    override var errorVisible by propertyBinding(BR.errorVisible, false)

    override var headerViewModel: DetailsHeaderViewModel? by propertyBinding(BR.headerViewModel, null)

    override var pullRequests by propertyBinding(BR.pullRequests, emptyList<PullRequestListItemViewModel>())

    private val eventPublisher = PublishSubject.create<DetailsEvent>()

    init {
        val disposable = interactor
            .createDetailsStateObservable(eventPublisher)
            .observeOn(schedulersProvider.main())
            .subscribe(::handleState, ::handleError)

        addDisposable(disposable)
    }

    override fun load(id: Long) {
        eventPublisher.onNext(DetailsEvent.Load(id))
    }

    private fun handleState(state: DetailsState) {
        when (state) {
            DetailsState.Loading -> showLoading()
            is DetailsState.Error -> handleError(state.throwable)
            is DetailsState.Success -> handleSuccess(state)
        }
    }

    private fun handleSuccess(successState: DetailsState.Success) {
        headerViewModel = DetailsHeaderViewModel(context, successState.repository, ::toggleBookmark)
        pullRequests = successState.pullRequests.map { it.toViewModel() }

        contentsVisible = true
        loadingVisible = false
    }

    private fun handleError(throwable: Throwable) {
        loadingVisible = false
        contentsVisible = false
        errorVisible = true

        Log.d(this::class.java.simpleName, throwable.message, throwable)
    }

    private fun showLoading() {
        loadingVisible = true
        contentsVisible = false
        errorVisible = false
    }

    private fun toggleBookmark(target: Repository) {
        eventPublisher.onNext(DetailsEvent.ToggleBookmark(target))
    }

    private fun PullRequest.toViewModel(): PullRequestListItemViewModel {
        return PullRequestListItemViewModel(context, this)
    }
}
