package dk.bluebox.demo.githubviewer.common.domain.models

import org.threeten.bp.LocalDateTime

data class PullRequest(
    val id: Long,
    val title: String,
    val number: Long,
    val state: State,
    val ownerName: String,
    val lastUpdate: LocalDateTime
) {

    enum class State {
        OPEN, CLOSED
    }
}
