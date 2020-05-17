package dk.bluebox.demo.githubviewer.features.details.ui.compose

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.layout.Column
import androidx.ui.layout.padding
import androidx.ui.material.Divider
import androidx.ui.material.MaterialTheme
import androidx.ui.res.stringResource
import androidx.ui.tooling.preview.Preview
import dk.bluebox.demo.githubviewer.R
import dk.bluebox.demo.githubviewer.common.domain.SampleData
import dk.bluebox.demo.githubviewer.common.domain.models.PullRequest
import dk.bluebox.demo.githubviewer.common.domain.models.Repository
import dk.bluebox.demo.githubviewer.common.ui.compose.Gaps
import dk.bluebox.demo.githubviewer.common.ui.compose.LoadingIndicator
import dk.bluebox.demo.githubviewer.features.details.domain.DetailsState

@Suppress("FunctionNaming")
@Composable
fun DetailsScreen(state: DetailsState, onToggleBookmark: (Repository) -> Unit = {}) {
    when (state) {
        DetailsState.Loading -> LoadingIndicator()
        is DetailsState.Success -> {
            DetailsScreen(state.repository, state.pullRequests, onToggleBookmark)
        }
    }
}

@Suppress("FunctionNaming")
@Composable
private fun DetailsScreen(
    repository: Repository,
    pullRequests: List<PullRequest>,
    onToggleBookmark: (Repository) -> Unit
) {
    val typography = MaterialTheme.typography
    val colors = MaterialTheme.colors

    Column {
        RepositoryHeader(repository, onToggleBookmark)
        Divider(
            color = colors.background,
            modifier = Modifier.padding(
                start = Gaps.DEFAULT,
                end = Gaps.DEFAULT,
                bottom = Gaps.XX_SMALL
            )
        )
        Text(
            text = stringResource(id = R.string.details_pull_requests_title),
            modifier = Modifier.padding(
                start = Gaps.DEFAULT,
                end = Gaps.DEFAULT,
                bottom = Gaps.X_SMALL
            ),
            style = typography.h6
        )
        PullRequestList(pullRequests)
    }
}

@Preview
@Suppress("FunctionNaming")
@Composable
private fun DetailsScreenPreview() {
    val state =
        DetailsState.Success(SampleData.REPOSITORY, SampleData.PULL_REQUESTS) as DetailsState

    DetailsScreen(state = state)
}
