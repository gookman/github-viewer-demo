package dk.bluebox.demo.githubviewer.features.list.ui

import android.content.Context
import android.util.Log
import dk.bluebox.demo.githubviewer.BR
import dk.bluebox.demo.githubviewer.R
import dk.bluebox.demo.githubviewer.features.list.domain.ListEvent
import dk.bluebox.demo.githubviewer.features.list.domain.ListInteractor
import dk.bluebox.demo.githubviewer.features.list.domain.ListState
import dk.bluebox.demo.githubviewer.common.domain.models.Repository
import dk.bluebox.demo.githubviewer.common.rx.SchedulersProvider
import dk.bluebox.demo.githubviewer.common.ui.core.databinding.propertyBinding
import dk.bluebox.demo.githubviewer.common.ui.DisposableCleaner
import io.reactivex.subjects.PublishSubject

class ListViewModelImpl(
    private val context: Context,
    interactor: ListInteractor,
    schedulersProvider: SchedulersProvider
) : ListViewModel() {

    override val title = context.getString(R.string.results_title)

    override var loadingVisible by propertyBinding(
        BR.loadingVisible,
        true
    )

    override var itemsVisible by propertyBinding(
        BR.itemsVisible,
        false
    )

    override var errorVisible by propertyBinding(
        BR.errorVisible,
        false
    )

    override var items by propertyBinding(
        BR.items,
        emptyList<RepositoryListItemViewModel>()
    )

    private val eventPublisher = PublishSubject.create<ListEvent>()

    override val cleaner =
        DisposableCleaner()

    init {
        val disposable = interactor
            .createListStateObservable(eventPublisher)
            .observeOn(schedulersProvider.main())
            .subscribe(::handleState, ::handleError)

        cleaner.add(disposable)
    }

    override fun load() {
        eventPublisher.onNext(ListEvent.Load)
    }

    private fun goToDetails(repo: Repository) {
        eventPublisher.onNext(ListEvent.Navigate(repo.id))
    }

    private fun handleState(state: ListState) {
        when (state) {
            ListState.Loading -> showLoading()
            is ListState.Error -> handleError(state.throwable)
            is ListState.Success -> handleSuccess(state)
        }
    }

    private fun handleSuccess(successState: ListState.Success) {
        items = successState.repositories.map {
            it.toViewModel()
        }
        itemsVisible = true
        loadingVisible = false
    }

    private fun handleError(throwable: Throwable) {
        loadingVisible = false
        itemsVisible = false
        errorVisible = true

        Log.d(this::class.java.simpleName, throwable.message, throwable)
    }

    private fun showLoading() {
        loadingVisible = true
        itemsVisible = false
        errorVisible = false
    }

    private fun Repository.toViewModel(): RepositoryListItemViewModel {
        return RepositoryListItemViewModel(
            context,
            this,
            ::goToDetails
        )
    }
}
