package dk.bluebox.demo.githubviewer.features.details.domain

import kotlinx.coroutines.flow.Flow

interface DetailsInteractor {
    fun createDetailsStateFlow(eventsFlow: Flow<DetailsEvent>): Flow<DetailsState>
}