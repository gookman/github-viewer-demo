package dk.bluebox.demo.githubviewer.features.list.ui

import androidx.databinding.Bindable
import dk.bluebox.demo.githubviewer.common.ui.utils.BaseViewModel

abstract class ListViewModel : BaseViewModel() {

    abstract val title: String

    @get:Bindable
    abstract val loadingVisible: Boolean

    @get:Bindable
    abstract val itemsVisible: Boolean

    @get:Bindable
    abstract val errorVisible: Boolean

    @get:Bindable
    abstract val items: List<RepositoryListItemViewModel>

    abstract fun load()
}
