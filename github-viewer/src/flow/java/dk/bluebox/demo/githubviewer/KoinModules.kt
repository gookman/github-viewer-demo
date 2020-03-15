package dk.bluebox.demo.githubviewer

import android.content.Context
import androidx.room.Room
import dk.bluebox.demo.githubviewer.common.data.GitHubRepository
import dk.bluebox.demo.githubviewer.common.data.DefaultGitHubRepository
import dk.bluebox.demo.githubviewer.common.data.room.AppDatabase
import dk.bluebox.demo.githubviewer.common.navigation.Router
import dk.bluebox.demo.githubviewer.common.navigation.RouterImpl
import dk.bluebox.demo.githubviewer.features.details.domain.DetailsInteractor
import dk.bluebox.demo.githubviewer.features.details.domain.DetailsInteractorImpl
import dk.bluebox.demo.githubviewer.features.list.domain.ListInteractor
import dk.bluebox.demo.githubviewer.features.list.domain.ListInteractorImpl
import dk.bluebox.demo.githubviewer.common.network.GitHubApi
import dk.bluebox.demo.githubviewer.common.network.GitHubApiImpl
import dk.bluebox.demo.githubviewer.common.network.ServiceFactory
import dk.bluebox.demo.githubviewer.common.network.ServiceFactoryImpl
import dk.bluebox.demo.githubviewer.features.details.ui.DetailsViewModelImpl
import dk.bluebox.demo.githubviewer.common.data.room.RoomGitHubRepository
import dk.bluebox.demo.githubviewer.features.details.ui.DetailsViewModel
import dk.bluebox.demo.githubviewer.features.list.ui.ListViewModel
import dk.bluebox.demo.githubviewer.features.list.ui.ListViewModelImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
@FlowPreview
val koinModule = module {

    viewModel {
        ListViewModelImpl(
            context = androidContext(),
            interactor = get()
        ) as ListViewModel
    }
    viewModel {
        DetailsViewModelImpl(
            context = androidContext(),
            interactor = get()
        ) as DetailsViewModel
    }

    single { RouterImpl(activity = get()) as Router }

    factory { ServiceFactoryImpl() as ServiceFactory }

    single { GitHubApiImpl(
        serviceFactory = get()
    ) as GitHubApi }

//    single { DefaultGitHubRepository(api = get()) as GitHubRepository }
    single {
        RoomGitHubRepository(
            defaultRepository = DefaultGitHubRepository(
                api = get()
            ),
            database = get()
        ) as GitHubRepository
    }

    single { ListInteractorImpl(
        router = get(),
        repository = get()
    ) as ListInteractor }

    single { DetailsInteractorImpl(
        repository = get()
    ) as DetailsInteractor }

    single { createDatabase(applicationContext = androidContext()) }
}

private fun createDatabase(applicationContext: Context): AppDatabase {
    return Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "github-viewer"
    ).build()
}