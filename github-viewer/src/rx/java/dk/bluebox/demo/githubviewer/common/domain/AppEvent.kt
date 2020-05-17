package dk.bluebox.demo.githubviewer.common.domain

import dk.bluebox.demo.githubviewer.features.details.domain.DetailsEvent
import dk.bluebox.demo.githubviewer.features.list.domain.ListEvent

sealed class AppEvent {
    data class List(val value: ListEvent) : AppEvent()
    data class Details(val value: DetailsEvent) : AppEvent()
    object Error : AppEvent()
}