package dk.bluebox.demo.githubviewer.flow.domain.list

import dk.bluebox.demo.githubviewer.common.domain.list.ListEvent
import dk.bluebox.demo.githubviewer.common.domain.list.ListState
import kotlinx.coroutines.flow.Flow

interface ListInteractor {
    fun createListStateFlow(eventsFlow: Flow<ListEvent>): Flow<ListState>
}