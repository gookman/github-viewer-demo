package dk.bluebox.demo.githubviewer.ui.details

import androidx.databinding.Bindable
import dk.bluebox.demo.githubviewer.ui.utils.BaseViewModel

abstract class DetailsViewModel : BaseViewModel() {
    abstract val title: String

    @get:Bindable
    abstract val loadingVisible: Boolean

    @get:Bindable
    abstract val contentsVisible: Boolean

    @get:Bindable
    abstract val errorVisible: Boolean

    @get:Bindable
    abstract val headerViewModel: DetailsHeaderViewModel?

    @get:Bindable
    abstract val pullRequests: List<PullRequestListItemViewModel>

    abstract fun load(id: Long)
}