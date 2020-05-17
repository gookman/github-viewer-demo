package dk.bluebox.demo.githubviewer.features.details.ui.compose

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.AdapterList
import androidx.ui.layout.padding
import androidx.ui.material.Divider
import androidx.ui.material.MaterialTheme
import androidx.ui.tooling.preview.Preview
import dk.bluebox.demo.githubviewer.common.domain.SampleData
import dk.bluebox.demo.githubviewer.common.domain.models.PullRequest
import dk.bluebox.demo.githubviewer.common.ui.compose.Gaps
import dk.bluebox.demo.githubviewer.common.ui.compose.wrapInListItem

@Suppress("FunctionNaming")
@Composable
fun PullRequestList(pullRequests: List<PullRequest>) {
    val colors = MaterialTheme.colors

    AdapterList(
        data = pullRequests.wrapInListItem(),
        modifier = Modifier.padding(start = Gaps.DEFAULT, end = Gaps.DEFAULT)
    ) { listItem ->
        PullRequestListItem(model = listItem.value)
        if (listItem.index < pullRequests.size - 1) {
            Divider(color = colors.background)
        }
    }
}

@Preview
@Suppress("FunctionNaming")
@Composable
fun PullRequestListPreview() {
    PullRequestList(pullRequests = SampleData.PULL_REQUESTS)
}
