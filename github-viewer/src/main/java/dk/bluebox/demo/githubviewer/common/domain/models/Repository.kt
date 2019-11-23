package dk.bluebox.demo.githubviewer.common.domain.models

import org.threeten.bp.LocalDateTime

data class Repository(
    val id: Long,
    val name: String,
    val ownerName: String,
    val starsCount: Long,
    val forksCount: Long,
    val watchersCount: Long,
    val openIssuesCount: Long,
    val description: String,
    val lastUpdate: LocalDateTime,
    val pullsUrl: String,
    val language: String,
    val bookmarked: Boolean
)
