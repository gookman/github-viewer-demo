package dk.bluebox.demo.githubviewer.features

import dk.bluebox.demo.githubviewer.common.domain.AppInteractor
import dk.bluebox.demo.githubviewer.common.domain.AppInteractorImpl
import dk.bluebox.demo.githubviewer.common.domain.AppState
import dk.bluebox.demo.githubviewer.features.details.domain.DetailsScreenInteractor
import dk.bluebox.demo.githubviewer.common.domain.KoinScreenInteractorFactory
import dk.bluebox.demo.githubviewer.common.domain.ScreenInteractorFactory
import dk.bluebox.demo.githubviewer.features.list.domain.ListScreenInteractor
import dk.bluebox.demo.githubviewer.features.list.domain.ListState
import org.koin.dsl.module

val APP_INITIAL_STATE = AppState.List(ListState.Loading)

val composeModule = module {
    single { ListScreenInteractor(wrappedInteractor = get()) }
    single {
        DetailsScreenInteractor(
            wrappedInteractor = get()
        )
    }

    single { KoinScreenInteractorFactory() as ScreenInteractorFactory }

    single {
        AppInteractorImpl(
            initialState = APP_INITIAL_STATE,
            screenInteractorFactory = get(),
            schedulersProvider = get()
        )  as AppInteractor
    }
}