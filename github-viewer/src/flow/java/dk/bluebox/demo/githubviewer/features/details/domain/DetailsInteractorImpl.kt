package dk.bluebox.demo.githubviewer.features.details.domain

import dk.bluebox.demo.githubviewer.common.domain.models.PullRequest
import dk.bluebox.demo.githubviewer.common.domain.models.Repository
import dk.bluebox.demo.githubviewer.common.data.GitHubRepository
import dk.bluebox.demo.githubviewer.common.mergeFlows
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flattenConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
class DetailsInteractorImpl @Inject constructor(
    private val repository: GitHubRepository
) : DetailsInteractor {

    override fun createDetailsStateFlow(eventsFlow: Flow<DetailsEvent>): Flow<DetailsState> {
        val toggleBookmarkFlow = eventsFlow
            .filter { it is DetailsEvent.ToggleBookmark }
            .flatMapLatest { createToggleBookmarkFlow((it as DetailsEvent.ToggleBookmark).target) }

        val loadFlow = eventsFlow
            .filter { it is DetailsEvent.Load }
            .flatMapLatest { createLoadFlow((it as DetailsEvent.Load).id) }

        return mergeFlows(
            toggleBookmarkFlow,
            loadFlow
        )
    }

    private fun createLoadFlow(id: Long): Flow<DetailsState> {
        return repository.getRepository(id)
            .flatMapLatest { repo ->
                repository.getPullRequests(repo.ownerName, repo.name)
                    .map { Pair(repo, it) }
            }
            .map<Pair<Repository, List<PullRequest>>, DetailsState> {
                DetailsState.Success(it.first, it.second)
            }
            .onStart { DetailsState.Loading }
    }

    private fun createToggleBookmarkFlow(item: Repository): Flow<DetailsState> {
        return repository.updateRepository(item.copy(bookmarked = item.bookmarked.not()))
            .map { createLoadFlow(item.id) }
            .flattenConcat()
    }
}