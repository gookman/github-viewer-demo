package dk.bluebox.demo.githubviewer.features.list.ui.compose

import androidx.compose.Composable
import androidx.ui.tooling.preview.Preview
import dk.bluebox.demo.githubviewer.common.domain.SampleData
import dk.bluebox.demo.githubviewer.common.navigation.Router
import dk.bluebox.demo.githubviewer.common.ui.compose.LoadingIndicator
import dk.bluebox.demo.githubviewer.features.list.domain.ListState

@Suppress("FunctionNaming")
@Composable
fun ListScreen(state: ListState, router: Router) {
    when (state) {
        ListState.Loading -> LoadingIndicator()
        is ListState.Success -> RepositoryList(
            state.repositories
        ) {
            router.goToDetails(it.id)
        }
    }
}

@Preview
@Suppress("FunctionNaming")
@Composable
private fun ListScreenPreview() {
    val state = ListState.Success(SampleData.REPOSITORIES) as ListState
    val router = object : Router { override fun goToDetails(id: Long) { /* not needed */ } }

    ListScreen(state = state, router = router)
}
