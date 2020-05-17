package dk.bluebox.demo.githubviewer.features.details.ui.compose

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.core.tag
import androidx.ui.foundation.Box
import androidx.ui.foundation.Text
import androidx.ui.layout.ConstraintLayout
import androidx.ui.layout.ConstraintSet
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.wrapContentHeight
import androidx.ui.material.MaterialTheme
import androidx.ui.res.stringResource
import androidx.ui.tooling.preview.Preview
import dk.bluebox.demo.githubviewer.R
import dk.bluebox.demo.githubviewer.common.domain.SampleData
import dk.bluebox.demo.githubviewer.common.domain.models.PullRequest
import dk.bluebox.demo.githubviewer.common.ui.compose.Gaps
import dk.bluebox.demo.githubviewer.common.ui.utils.SIMPLE_ISO_DATE_TIME

@Suppress("FunctionNaming")
@Composable
fun PullRequestListItem(model: PullRequest) {
    val typography = MaterialTheme.typography

    Box(padding = Gaps.DEFAULT) {
        ConstraintLayout(
            modifier = Modifier.wrapContentHeight(Alignment.CenterVertically) + Modifier.fillMaxWidth(),
            constraintSet = ConstraintSet {
                val number = tag(ItemTags.NUMBER)
                val title = tag(ItemTags.TITLE)
                val owner = tag(ItemTags.OWNER)
                val state = tag(ItemTags.STATE)
                val lastUpdate = tag(ItemTags.LAST_UPDATE)

                number.apply {
                    left constrainTo parent.left
                    top constrainTo parent.top
                }

                title.apply {
                    left constrainTo parent.left
                    top constrainTo number.bottom
                }

                owner.apply {
                    left constrainTo parent.left
                    top constrainTo title.bottom
                }

                state.apply {
                    top constrainTo parent.top
                    right constrainTo parent.right
                }

                lastUpdate.apply {
                    top constrainTo title.bottom
                    right constrainTo parent.right
                }
            }) {
            Text(
                modifier = Modifier.tag(ItemTags.NUMBER),
                text = model.number.toString(),
                style = typography.subtitle1
            )
            Text(
                modifier = Modifier.tag(ItemTags.TITLE),
                text = model.title,
                style = typography.subtitle1
            )
            Text(
                modifier = Modifier.tag(ItemTags.OWNER),
                text = model.ownerName,
                style = typography.subtitle2
            )
            Text(
                modifier = Modifier.tag(ItemTags.STATE),
                text = model.state.toString(),
                style = typography.subtitle2
            )
            Text(
                modifier = Modifier.tag(ItemTags.LAST_UPDATE),
                text = stringResource(
                    R.string.results_updated, model.lastUpdate.format(
                        SIMPLE_ISO_DATE_TIME
                    )),
                style = typography.overline
            )
        }
    }
}

@Preview
@Suppress("FunctionNaming")
@Composable
private fun PullRequestListItemPreview() {
    PullRequestListItem(model = SampleData.PULL_REQUEST)
}

private object ItemTags {
    const val NUMBER = "number"
    const val TITLE = "title"
    const val OWNER = "owner"
    const val STATE = "state"
    const val LAST_UPDATE = "lastUpdate"
}
