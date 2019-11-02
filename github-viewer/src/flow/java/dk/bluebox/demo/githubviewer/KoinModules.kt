package dk.bluebox.demo.githubviewer

import android.content.Context
import androidx.room.Room
import dk.bluebox.demo.githubviewer.flow.data.GitHubRepository
import dk.bluebox.demo.githubviewer.flow.data.DefaultGitHubRepository
import dk.bluebox.demo.githubviewer.flow.data.room.AppDatabase
import dk.bluebox.demo.githubviewer.common.navigation.Router
import dk.bluebox.demo.githubviewer.common.navigation.RouterImpl
import dk.bluebox.demo.githubviewer.flow.domain.details.DetailsInteractor
import dk.bluebox.demo.githubviewer.flow.domain.details.DetailsInteractorImpl
import dk.bluebox.demo.githubviewer.flow.domain.list.ListInteractor
import dk.bluebox.demo.githubviewer.flow.domain.list.ListInteractorImpl
import dk.bluebox.demo.githubviewer.flow.network.GitHubApi
import dk.bluebox.demo.githubviewer.flow.network.GitHubApiImpl
import dk.bluebox.demo.githubviewer.common.network.ServiceFactory
import dk.bluebox.demo.githubviewer.flow.network.ServiceFactoryImpl
import dk.bluebox.demo.githubviewer.common.ui.details.DetailsViewModel
import dk.bluebox.demo.githubviewer.flow.ui.details.DetailsViewModelImpl
import dk.bluebox.demo.githubviewer.common.ui.list.ListViewModel
import dk.bluebox.demo.githubviewer.flow.data.room.RoomGitHubRepository
import dk.bluebox.demo.githubviewer.flow.ui.list.ListViewModelImpl
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

    single { GitHubApiImpl(serviceFactory = get()) as GitHubApi }

//    single { DefaultGitHubRepository(api = get()) as GitHubRepository }
    single {
        RoomGitHubRepository(
            defaultRepository = DefaultGitHubRepository(api = get()),
            database = get()
        ) as GitHubRepository
    }

    single { ListInteractorImpl(router = get(), repository = get()) as ListInteractor }

    single { DetailsInteractorImpl(repository = get()) as DetailsInteractor }

    single { createDatabase(applicationContext = androidContext()) }
}

private fun createDatabase(applicationContext: Context): AppDatabase {
    return Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "github-viewer"
    ).build()
}