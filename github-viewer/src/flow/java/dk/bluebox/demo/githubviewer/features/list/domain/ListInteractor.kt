package dk.bluebox.demo.githubviewer.features.list.domain

import kotlinx.coroutines.flow.Flow

interface ListInteractor {
    fun createListStateFlow(eventsFlow: Flow<ListEvent>): Flow<ListState>
}