package dk.bluebox.demo.githubviewer.ui.list

import androidx.databinding.Bindable
import dk.bluebox.demo.githubviewer.ui.utils.BaseViewModel

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