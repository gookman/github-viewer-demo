package dk.bluebox.demo.githubviewer.features.list.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import dk.bluebox.demo.githubviewer.BR
import dk.bluebox.demo.githubviewer.R
import dk.bluebox.demo.githubviewer.features.list.domain.ListInteractor
import dk.bluebox.demo.githubviewer.common.domain.models.Repository
import dk.bluebox.demo.githubviewer.common.ui.ChannelCleaner
import dk.bluebox.demo.githubviewer.common.ui.core.databinding.propertyBinding
import dk.bluebox.demo.githubviewer.features.list.domain.ListEvent
import dk.bluebox.demo.githubviewer.features.list.domain.ListState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class ListViewModelImpl @Inject constructor(
    private val context: Context,
    interactor: ListInteractor
) : ListViewModel() {

    override val title = context.getString(R.string.results_title)

    override var loadingVisible by propertyBinding(BR.loadingVisible, true)

    override var itemsVisible by propertyBinding(BR.itemsVisible, false)

    override var errorVisible by propertyBinding(BR.errorVisible, false)

    override var items by propertyBinding(BR.items, emptyList<RepositoryListItemViewModel>())

    private val channel = BroadcastChannel<ListEvent>(10)

    override val cleaner =
        ChannelCleaner()

    init {
        viewModelScope.launch {
            interactor.createListStateFlow(channel.asFlow())
                .onEach { handleState(it) }
                .catch { handleError(it) }
                .collect()
        }

        cleaner.add(channel)
    }

    override fun load() {
        channel.offer(ListEvent.Load)
    }

    private fun goToDetails(repo: Repository) {
        channel.offer(ListEvent.Navigate(repo.id))
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
