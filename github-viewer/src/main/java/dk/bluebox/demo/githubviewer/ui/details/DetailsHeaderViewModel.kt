package dk.bluebox.demo.githubviewer.ui.details

import android.content.Context
import dk.bluebox.demo.githubviewer.R
import dk.bluebox.demo.githubviewer.domain.models.Repository
import dk.bluebox.demo.githubviewer.ui.utils.SIMPLE_ISO_DATE_TIME

class DetailsHeaderViewModel(context: Context, repo: Repository) {
    val name = repo.name
    val owner = repo.ownerName
    val description = repo.description
    val updated = context.getString(R.string.details_updated, repo.lastUpdate.format(SIMPLE_ISO_DATE_TIME))
    val openIssues = context.getString(R.string.details_open_issues, repo.openIssuesCount)
    val language = repo.language
    val stars = repo.starsCount.toString()
    val watchers = repo.watchersCount.toString()
    val forks = repo.forksCount.toString()
}