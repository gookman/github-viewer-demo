package dk.bluebox.demo.githubviewer.features.details.ui

import android.content.Context
import android.util.Log
import dk.bluebox.demo.githubviewer.BR
import dk.bluebox.demo.githubviewer.R
import dk.bluebox.demo.githubviewer.features.details.domain.DetailsEvent
import dk.bluebox.demo.githubviewer.features.details.domain.DetailsInteractor
import dk.bluebox.demo.githubviewer.features.details.domain.DetailsState
import dk.bluebox.demo.githubviewer.common.domain.models.PullRequest
import dk.bluebox.demo.githubviewer.common.domain.models.Repository
import dk.bluebox.demo.githubviewer.common.rx.SchedulersProvider
import dk.bluebox.demo.githubviewer.common.ui.core.databinding.propertyBinding
import dk.bluebox.demo.githubviewer.common.ui.DisposableCleaner
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class DetailsViewModelImpl @Inject constructor(
    private val context: Context,
    interactor: DetailsInteractor,
    schedulersProvider: SchedulersProvider
) : DetailsViewModel() {
    override val title = context.getString(R.string.details_title)

    override var loadingVisible by propertyBinding(
        BR.loadingVisible,
        true
    )

    override var contentsVisible by propertyBinding(
        BR.contentsVisible,
        false
    )

    override var errorVisible by propertyBinding(
        BR.errorVisible,
        false
    )

    override var headerViewModel: DetailsHeaderViewModel? by propertyBinding(
        BR.headerViewModel,
        null
    )

    override var pullRequests by propertyBinding(
        BR.pullRequests,
        emptyList<PullRequestListItemViewModel>()
    )

    private val eventPublisher = PublishSubject.create<DetailsEvent>()

    override val cleaner =
        DisposableCleaner()

    init {
        val disposable = interactor
            .createDetailsStateObservable(eventPublisher)
            .observeOn(schedulersProvider.main())
            .subscribe(::handleState, ::handleError)

        cleaner.add(disposable)
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
        headerViewModel =
            DetailsHeaderViewModel(
                context,
                successState.repository,
                ::toggleBookmark
            )
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
        return PullRequestListItemViewModel(
            context,
            this
        )
    }
}
