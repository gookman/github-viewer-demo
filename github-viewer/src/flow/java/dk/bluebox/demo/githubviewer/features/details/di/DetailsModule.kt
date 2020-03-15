package dk.bluebox.demo.githubviewer.features.details.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dk.bluebox.demo.githubviewer.common.data.GitHubRepository
import dk.bluebox.demo.githubviewer.common.di.DataModuleConstants
import dk.bluebox.demo.githubviewer.common.di.FragmentScope
import dk.bluebox.demo.githubviewer.common.ui.core.BaseViewModelFactory
import dk.bluebox.demo.githubviewer.features.details.domain.DetailsInteractor
import dk.bluebox.demo.githubviewer.features.details.domain.DetailsInteractorImpl
import dk.bluebox.demo.githubviewer.features.details.ui.DetailsFragment
import dk.bluebox.demo.githubviewer.features.details.ui.DetailsViewModel
import dk.bluebox.demo.githubviewer.features.details.ui.DetailsViewModelImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Named

@ExperimentalCoroutinesApi
@FlowPreview
@Module
abstract class DetailsModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [SubComponentModule::class])
    abstract fun fragment(): DetailsFragment

    @Module
    class SubComponentModule {
        @FragmentScope
        @Provides
        fun providesViewModelFactory(
            context: Context,
            interactor: DetailsInteractor
        ): BaseViewModelFactory<DetailsViewModel> {
            return BaseViewModelFactory {
                DetailsViewModelImpl(context, interactor)
            }
        }

        @FragmentScope
        @Provides
        fun providesInteractor(
            @Named(DataModuleConstants.REPOSITORY) repository: GitHubRepository
        ): DetailsInteractor {
            return DetailsInteractorImpl(repository)
        }

        @FragmentScope
        @Provides
        fun providesDetailsViewModel(
            factory: BaseViewModelFactory<DetailsViewModel>,
            target: DetailsFragment
        ): DetailsViewModel {
            return ViewModelProvider(target, factory).get(DetailsViewModel::class.java)
        }
    }
}