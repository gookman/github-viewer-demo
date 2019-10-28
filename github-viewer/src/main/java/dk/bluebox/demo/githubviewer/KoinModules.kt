package dk.bluebox.demo.githubviewer

import android.content.Context
import androidx.room.Room
import dk.bluebox.demo.githubviewer.data.GitHubRepository
import dk.bluebox.demo.githubviewer.data.DefaultGitHubRepository
import dk.bluebox.demo.githubviewer.data.room.AppDatabase
import dk.bluebox.demo.githubviewer.data.room.RoomGitHubRepository
import dk.bluebox.demo.githubviewer.navigation.Router
import dk.bluebox.demo.githubviewer.navigation.RouterImpl
import dk.bluebox.demo.githubviewer.domain.details.DetailsInteractor
import dk.bluebox.demo.githubviewer.domain.details.DetailsInteractorImpl
import dk.bluebox.demo.githubviewer.domain.list.ListInteractor
import dk.bluebox.demo.githubviewer.domain.list.ListInteractorImpl
import dk.bluebox.demo.githubviewer.network.GitHubApi
import dk.bluebox.demo.githubviewer.network.GitHubApiImpl
import dk.bluebox.demo.githubviewer.network.ServiceFactory
import dk.bluebox.demo.githubviewer.network.ServiceFactoryImpl
import dk.bluebox.demo.githubviewer.rx.AndroidSchedulersProvider
import dk.bluebox.demo.githubviewer.rx.SchedulersProvider
import dk.bluebox.demo.githubviewer.ui.details.DetailsViewModelImpl
import dk.bluebox.demo.githubviewer.ui.list.ListViewModel
import dk.bluebox.demo.githubviewer.ui.list.ListViewModelImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val koinModule = module {

    viewModel { ListViewModelImpl(context = androidContext(), interactor = get(), schedulersProvider = get()) as ListViewModel }
    viewModel { DetailsViewModelImpl(context = androidContext(), interactor = get(), schedulersProvider = get()) }

    single { RouterImpl(activity = get()) as Router }
    single { AndroidSchedulersProvider() as SchedulersProvider }

    factory { ServiceFactoryImpl(schedulersProvider = get()) as ServiceFactory }

    single { GitHubApiImpl(serviceFactory = get()) as GitHubApi }

    //single { DefaultGitHubRepository(api = get()) as GitHubRepository }
    single { RoomGitHubRepository(
            defaultRepository = DefaultGitHubRepository(api = get()),
            database = get(),
            schedulersProvider = get()
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