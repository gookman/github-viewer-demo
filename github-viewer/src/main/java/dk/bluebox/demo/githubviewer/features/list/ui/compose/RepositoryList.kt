package dk.bluebox.demo.githubviewer.features.list.ui.compose

import androidx.compose.Composable
import androidx.ui.foundation.AdapterList
import androidx.ui.material.Divider
import androidx.ui.material.MaterialTheme
import androidx.ui.tooling.preview.Preview
import dk.bluebox.demo.githubviewer.common.domain.SampleData
import dk.bluebox.demo.githubviewer.common.domain.models.Repository
import dk.bluebox.demo.githubviewer.common.ui.compose.wrapInListItem

@Suppress("FunctionNaming")
@Composable
fun RepositoryList(repositories: List<Repository>, onClick: (Repository) -> Unit = {}) {
    val colors = MaterialTheme.colors

    AdapterList(data = repositories.wrapInListItem()) { listItem ->
        RepositoryListItem(model = listItem.value, onClick = onClick)
        if (listItem.index < repositories.size - 1) {
            Divider(color = colors.background)
        }
    }
}

@Preview
@Suppress("FunctionNaming")
@Composable
private fun RepositoryListPreview() {
    RepositoryList(repositories = SampleData.REPOSITORIES)
}
