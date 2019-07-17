package dk.bluebox.demo.githubviewer.ui.details

import android.content.Context
import dk.bluebox.demo.githubviewer.BR
import dk.bluebox.demo.githubviewer.R
import dk.bluebox.demo.githubviewer.domain.models.PullRequest
import dk.bluebox.demo.githubviewer.ui.utils.SIMPLE_ISO_DATE_TIME
import dk.bluebox.demo.githubviewer.ui.utils.bindingadapter.DataBindingInfo
import dk.bluebox.demo.githubviewer.ui.utils.bindingadapter.DataBindingItem

class PullRequestListItemViewModel(
    context: Context,
    private val item: PullRequest
) : DataBindingItem {

    val name = item.title
    val owner = item.ownerName
    val number = item.number.toString()
    val updated = context.getString(R.string.details_updated, item.lastUpdate.format(SIMPLE_ISO_DATE_TIME))
    val state = item.state.toString()

    override val dataBindingInfo: DataBindingInfo
        get() = DataBindingInfo(R.layout.layout_pull_request_list_item, BR.viewModel)
}