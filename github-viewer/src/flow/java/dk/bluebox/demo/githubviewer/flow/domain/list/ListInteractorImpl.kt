package dk.bluebox.demo.githubviewer.flow.domain.list

import dk.bluebox.demo.githubviewer.common.domain.list.ListEvent
import dk.bluebox.demo.githubviewer.common.domain.list.ListState
import dk.bluebox.demo.githubviewer.common.navigation.Router
import dk.bluebox.demo.githubviewer.flow.data.GitHubRepository
import dk.bluebox.demo.githubviewer.flow.mergeFlows
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

@ExperimentalCoroutinesApi
@FlowPreview
class ListInteractorImpl(
    private val router: Router,
    private val repository: GitHubRepository
) : ListInteractor {

    override fun createListStateFlow(eventsFlow: Flow<ListEvent>): Flow<ListState> {
        val loadFilter = eventsFlow
            .filter { it is ListEvent.Load }
            .flatMapLatest { createLoadFlow() }

        val navigateFilter = eventsFlow
            .filter { it is ListEvent.Navigate }
            .onEach { router.goToDetails((it as ListEvent.Navigate).id) }
            .map<ListEvent, ListState> { ListState.Loading }

        return mergeFlows(loadFilter, navigateFilter)
            .catch { emit(ListState.Error(it)) }
    }

    private fun createLoadFlow(): Flow<ListState> {
        return repository
            .getRepositories()
            .map { ListState.Success(it) }
            .onStart { ListState.Loading }
    }
}