package dk.bluebox.demo.githubviewer.ui.list

import android.content.Context
import dk.bluebox.demo.githubviewer.BR
import dk.bluebox.demo.githubviewer.R
import dk.bluebox.demo.githubviewer.domain.models.Repository
import dk.bluebox.demo.githubviewer.ui.utils.SIMPLE_ISO_DATE_TIME
import dk.bluebox.demo.githubviewer.ui.utils.bindingadapter.DataBindingInfo
import dk.bluebox.demo.githubviewer.ui.utils.bindingadapter.DataBindingItem

class RepositoryListItemViewModel(
    context: Context,
    private val item: Repository,
    val onClick: (Repository) -> Unit
) : DataBindingItem {

    val name = item.name
    val owner = item.ownerName
    val starsCount = item.starsCount.toString()
    val lastUpdate = context.getString(R.string.results_updated, item.lastUpdate.format(SIMPLE_ISO_DATE_TIME))

    override val dataBindingInfo: DataBindingInfo
        get() = DataBindingInfo(R.layout.layout_repository_list_item, BR.viewModel)

    fun onClick() {
        onClick(item)
    }
}