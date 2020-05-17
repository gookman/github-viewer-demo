package dk.bluebox.demo.githubviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.Composable
import androidx.compose.State
import androidx.ui.core.setContent
import androidx.ui.material.MaterialTheme
import androidx.ui.rxjava2.subscribeAsState
import dk.bluebox.demo.githubviewer.common.domain.AppInteractor
import dk.bluebox.demo.githubviewer.common.domain.AppState
import dk.bluebox.demo.githubviewer.common.ui.compose.ErrorScreen
import dk.bluebox.demo.githubviewer.common.ui.compose.appColorPalette
import dk.bluebox.demo.githubviewer.features.list.ui.compose.ListScreen
import dk.bluebox.demo.githubviewer.features.APP_INITIAL_STATE
import dk.bluebox.demo.githubviewer.features.composeModule
import dk.bluebox.demo.githubviewer.features.details.ui.compose.DetailsScreen
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.dsl.module

class MainActivity : AppCompatActivity() {
    private val appInteractor by inject<AppInteractor>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setupKoin()
        super.onCreate(savedInstanceState)

        onBackPressedDispatcher.addCallback(appInteractor.onBackPressedCallback)

        setContent {
            val state = appInteractor.appStateObservable
                .subscribeAsState(APP_INITIAL_STATE)

            MaterialTheme(colors = appColorPalette) {
                MainScreen(state)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidLogger()
            androidContext(this@MainActivity)

            modules(listOf(koinModule, composeModule, getActivityModule()))
        }
    }

    @Suppress("FunctionNaming")
    @Composable
    fun MainScreen(state: State<AppState>) {
        when (val appState = state.value) {
            is AppState.List -> ListScreen(state = appState.value, router = appInteractor)
            is AppState.Details -> {
                DetailsScreen(
                    state = appState.value,
                    onToggleBookmark = { repository -> appInteractor.toggleBookmark(repository) })
            }
            is AppState.Error -> ErrorScreen { appInteractor.retryLast() }
        }
    }

    private fun getActivityModule(): Module {
        return module { single { this@MainActivity as AppCompatActivity } }
    }
}
