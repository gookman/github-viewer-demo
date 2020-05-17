package dk.bluebox.demo.githubviewer.common.domain

import dk.bluebox.demo.githubviewer.features.details.domain.DetailsState
import dk.bluebox.demo.githubviewer.features.list.domain.ListState

sealed class AppState {
    data class List(val value: ListState) : AppState()
    data class Details(val value: DetailsState) : AppState()
    data class Error(val throwable: Throwable) : AppState()
}
