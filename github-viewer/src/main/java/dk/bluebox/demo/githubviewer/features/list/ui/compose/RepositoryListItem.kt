package dk.bluebox.demo.githubviewer.features.list.ui.compose

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.core.tag
import androidx.ui.foundation.Box
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.graphics.vector.VectorPainter
import androidx.ui.layout.ConstraintLayout
import androidx.ui.layout.ConstraintSet
import androidx.ui.layout.ConstraintSetBuilderScope
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.wrapContentHeight
import androidx.ui.material.MaterialTheme
import androidx.ui.material.ripple.ripple
import androidx.ui.res.stringResource
import androidx.ui.res.vectorResource
import androidx.ui.tooling.preview.Preview
import dk.bluebox.demo.githubviewer.R
import dk.bluebox.demo.githubviewer.common.domain.SampleData
import dk.bluebox.demo.githubviewer.common.domain.models.Repository
import dk.bluebox.demo.githubviewer.common.ui.compose.Gaps
import dk.bluebox.demo.githubviewer.common.ui.utils.SIMPLE_ISO_DATE_TIME

@Suppress("FunctionNaming")
@Composable
fun RepositoryListItem(model: Repository, onClick: (Repository) -> Unit = {}) {
    val typography = MaterialTheme.typography

    Clickable(onClick = { onClick(model) }, modifier = Modifier.ripple()) {
        Box(padding = Gaps.DEFAULT) {
            ConstraintLayout(
                modifier = Modifier.wrapContentHeight(Alignment.CenterVertically) + Modifier.fillMaxWidth(),
                constraintSet = ConstraintSet {
                val name = tag(Tags.NAME)
                val owner = tag(Tags.OWNER)
                val starsCount = tag(Tags.STARS_COUNT)
                val lastUpdate = tag(Tags.LAST_UPDATE)
                val starsImage = tag(Tags.STARS_IMAGE)
                val barrier = createRightBarrier(starsCount, lastUpdate)

                name.apply {
                    left constrainTo parent.left
                    top constrainTo parent.top
                    right constrainTo barrier
                    bottom constrainTo owner.top
                    horizontalBias = 0F
                }

                owner.apply {
                    left constrainTo parent.left
                    top constrainTo name.bottom
                    right constrainTo barrier
                    bottom constrainTo parent.bottom
                    horizontalBias = 0F
                }

                createVerticalChain(
                    name,
                    owner,
                    chainStyle = ConstraintSetBuilderScope.ChainStyle.Packed
                )

                starsCount.apply {
                    top constrainTo parent.top
                    right constrainTo parent.right
                    bottom constrainTo lastUpdate.top
                    right.margin = Gaps.SMALL
                }

                lastUpdate.apply {
                    top constrainTo starsCount.bottom
                    right constrainTo parent.right
                    bottom constrainTo parent.bottom
                }

                createVerticalChain(
                    starsCount,
                    lastUpdate,
                    chainStyle = ConstraintSetBuilderScope.ChainStyle.Packed
                )

                starsImage.apply {
                    left constrainTo starsCount.right
                    top constrainTo starsCount.top
                    right constrainTo parent.right
                    bottom constrainTo starsCount.bottom
                    left.margin = Gaps.SMALL
                }
            }) {
                Text(
                    modifier = Modifier.tag(Tags.NAME),
                    text = model.name,
                    style = typography.subtitle1
                )
                Text(
                    modifier = Modifier.tag(Tags.OWNER),
                    text = model.ownerName,
                    style = typography.subtitle2
                )
                Text(
                    modifier = Modifier.tag(Tags.STARS_COUNT),
                    text = model.starsCount.toString(),
                    style = typography.subtitle2
                )
                Text(
                    modifier = Modifier.tag(Tags.LAST_UPDATE),
                    text = stringResource(R.string.results_updated, model.lastUpdate.format(SIMPLE_ISO_DATE_TIME)),
                    style = typography.overline
                )

                val starsImage = vectorResource(id = R.drawable.ic_star)
                Image(
                    modifier = Modifier.tag(Tags.STARS_IMAGE),
                    painter = VectorPainter(asset = starsImage)
                )
            }
        }
    }
}

@Preview
@Suppress("FunctionNaming")
@Composable
private fun RepositoryListItemPreview() {
    RepositoryListItem(
        model = SampleData.REPOSITORY
    )
}

private object Tags {
    const val NAME = "name"
    const val OWNER = "owner"
    const val STARS_COUNT = "starsCount"
    const val LAST_UPDATE = "lastUpdate"
    const val STARS_IMAGE = "starsImage"
}
