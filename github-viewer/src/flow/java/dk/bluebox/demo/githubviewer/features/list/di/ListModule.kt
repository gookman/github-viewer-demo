package dk.bluebox.demo.githubviewer.features.list.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dk.bluebox.demo.githubviewer.common.data.GitHubRepository
import dk.bluebox.demo.githubviewer.common.di.DataModuleConstants
import dk.bluebox.demo.githubviewer.common.di.FragmentScope
import dk.bluebox.demo.githubviewer.common.navigation.Router
import dk.bluebox.demo.githubviewer.common.ui.core.BaseViewModelFactory
import dk.bluebox.demo.githubviewer.features.list.domain.ListInteractor
import dk.bluebox.demo.githubviewer.features.list.domain.ListInteractorImpl
import dk.bluebox.demo.githubviewer.features.list.ui.ListFragment
import dk.bluebox.demo.githubviewer.features.list.ui.ListViewModel
import dk.bluebox.demo.githubviewer.features.list.ui.ListViewModelImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Named

@ExperimentalCoroutinesApi
@FlowPreview
@Module
abstract class ListModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [SubComponentModule::class])
    abstract fun fragment(): ListFragment

    @Module
    class SubComponentModule {
        @FragmentScope
        @Provides
        fun providesViewModelFactory(
            context: Context,
            interactor: ListInteractor
        ): BaseViewModelFactory<ListViewModel> {
            return BaseViewModelFactory {
                ListViewModelImpl(context, interactor)
            }
        }

        @FragmentScope
        @Provides
        fun providesInteractor(
            router: Router,
            @Named(DataModuleConstants.REPOSITORY) repository: GitHubRepository
        ): ListInteractor {
            return ListInteractorImpl(router, repository)
        }

        @FragmentScope
        @Provides
        fun providesListViewModel(
            factory: BaseViewModelFactory<ListViewModel>,
            target: ListFragment
        ): ListViewModel {
            return ViewModelProvider(target, factory).get(ListViewModel::class.java)
        }
    }
}
