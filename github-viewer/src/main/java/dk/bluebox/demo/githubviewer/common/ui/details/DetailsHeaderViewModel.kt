package dk.bluebox.demo.githubviewer.common.ui.details

import android.content.Context
import dk.bluebox.demo.githubviewer.R
import dk.bluebox.demo.githubviewer.common.domain.models.Repository
import dk.bluebox.demo.githubviewer.common.ui.utils.SIMPLE_ISO_DATE_TIME

class DetailsHeaderViewModel(
    context: Context,
    private val repo: Repository,
    private val toggleBookmark: (Repository) -> Unit = {}) {

    val name = repo.name
    val owner = repo.ownerName
    val description = repo.description
    val updated = context.getString(R.string.details_updated, repo.lastUpdate.format(SIMPLE_ISO_DATE_TIME))
    val openIssues = context.getString(R.string.details_open_issues, repo.openIssuesCount)
    val language = repo.language
    val stars = repo.starsCount.toString()
    val watchers = repo.watchersCount.toString()
    val forks = repo.forksCount.toString()
    val bookmarkIcon = if (repo.bookmarked) R.drawable.ic_bookmark else R.drawable.ic_bookmark_border

    fun onBookmarkClicked() {
        toggleBookmark(repo)
    }
}