package dk.bluebox.demo.githubviewer.flow.domain.details

import dk.bluebox.demo.githubviewer.common.domain.details.DetailsEvent
import dk.bluebox.demo.githubviewer.common.domain.details.DetailsState
import kotlinx.coroutines.flow.Flow

interface DetailsInteractor {
    fun createDetailsStateFlow(eventsFlow: Flow<DetailsEvent>): Flow<DetailsState>
}