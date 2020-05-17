package dk.bluebox.demo.githubviewer.common.domain

import dk.bluebox.demo.githubviewer.common.domain.models.PullRequest
import dk.bluebox.demo.githubviewer.common.domain.models.Repository
import org.threeten.bp.LocalDateTime

object SampleData {
    val REPOSITORY = Repository(
        id = 3245,
        name = "My special repo",
        ownerName = "Jon",
        starsCount = 100,
        forksCount = 67,
        watchersCount = 125,
        openIssuesCount = 21,
        description = "My repo that does special things",
        lastUpdate = LocalDateTime.of(2010, 10, 10, 10, 10),
        pullsUrl = "",
        language = "kotlin",
        bookmarked = false
    )

    val REPOSITORIES = generateSequence { REPOSITORY }.take(10).toList()

    val PULL_REQUEST = PullRequest(
        id = 2232,
        title = "Fix for background colour",
        number = 22,
        state = PullRequest.State.OPEN,
        ownerName = "Jon",
        lastUpdate = LocalDateTime.of(2009, 9, 9, 9, 9)
    )

    val PULL_REQUESTS = generateSequence { PULL_REQUEST }.take(10).toList()
}