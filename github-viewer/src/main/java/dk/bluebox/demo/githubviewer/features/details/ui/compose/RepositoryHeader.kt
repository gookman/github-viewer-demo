package dk.bluebox.demo.githubviewer.features.details.ui.compose

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.core.tag
import androidx.ui.foundation.Box
import androidx.ui.foundation.ContentGravity
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.foundation.selection.ToggleableState
import androidx.ui.foundation.selection.TriStateToggleable
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
import androidx.ui.semantics.Semantics
import androidx.ui.tooling.preview.Preview
import dk.bluebox.demo.githubviewer.R
import dk.bluebox.demo.githubviewer.common.domain.SampleData
import dk.bluebox.demo.githubviewer.common.domain.models.Repository
import dk.bluebox.demo.githubviewer.common.ui.compose.Gaps
import dk.bluebox.demo.githubviewer.common.ui.utils.SIMPLE_ISO_DATE_TIME

@Suppress("FunctionNaming")
@Composable
fun RepositoryHeader(model: Repository, onBookmarkClick: (Repository) -> Unit = {}) {
    val typography = MaterialTheme.typography

    Box(padding = Gaps.DEFAULT) {
        ConstraintLayout(
            modifier = Modifier.wrapContentHeight(Alignment.CenterVertically) + Modifier.fillMaxWidth(),
            constraintSet = ConstraintSet {
                val name = tag(HeaderTags.NAME)
                val owner = tag(HeaderTags.OWNER)
                val description = tag(HeaderTags.DESCRIPTION)
                val lastUpdate = tag(HeaderTags.LAST_UPDATE)
                val openIssues = tag(HeaderTags.OPEN_ISSUES)
                val language = tag(HeaderTags.LANGUAGE)
                val starsCount = tag(HeaderTags.STARS)
                val starsImage = tag(HeaderTags.STARS_IMAGE)
                val watchersCount = tag(HeaderTags.WATCHERS)
                val watchersImage = tag(HeaderTags.WATCHERS_IMAGE)
                val forksCount = tag(HeaderTags.FORKS)
                val forksImage = tag(HeaderTags.FORKS_IMAGE)
                val bookmarkToggle = tag(HeaderTags.BOOKMARK_TOGGLE)

                val iconsBarrier = createLeftBarrier(starsCount, watchersCount, forksCount)

                name.apply {
                    left constrainTo parent.left
                    top constrainTo parent.top
                    right constrainTo iconsBarrier
                    horizontalBias = 0F
                }

                owner.apply {
                    left constrainTo parent.left
                    top constrainTo name.bottom
                    right constrainTo iconsBarrier
                    horizontalBias = 0F
                }

                description.apply {
                    left constrainTo parent.left
                    top constrainTo owner.bottom
                    right constrainTo iconsBarrier
                    top.margin = Gaps.X_SMALL
                    horizontalBias = 0F
                }

                createHorizontalChain(
                    lastUpdate,
                    openIssues,
                    language,
                    chainStyle = ConstraintSetBuilderScope.ChainStyle.Packed
                )

                lastUpdate.apply {
                    left constrainTo parent.left
                    top constrainTo description.bottom
                    right constrainTo openIssues.left
                    top.margin = Gaps.X_SMALL
                    horizontalBias = 0F
                }

                openIssues.apply {
                    left constrainTo lastUpdate.right
                    top constrainTo description.bottom
                    right constrainTo language.left
                    top.margin = Gaps.X_SMALL
                    left.margin = Gaps.X_SMALL
                }

                language.apply {
                    left constrainTo openIssues.right
                    top constrainTo description.bottom
                    left.margin = Gaps.X_SMALL
                    top.margin = Gaps.X_SMALL
                    right constrainTo iconsBarrier
                }

                starsCount.apply {
                    top constrainTo parent.top
                    right constrainTo starsImage.left
                    right.margin = Gaps.SMALL
                }

                starsImage.apply {
                    top constrainTo starsCount.top
                    right constrainTo parent.right
                    bottom constrainTo starsCount.bottom
                }

                watchersCount.apply {
                    top constrainTo starsCount.bottom
                    right constrainTo watchersImage.left
                    right.margin = Gaps.SMALL
                }

                watchersImage.apply {
                    top constrainTo watchersCount.top
                    right constrainTo parent.right
                    bottom constrainTo watchersCount.bottom
                }

                forksCount.apply {
                    top constrainTo watchersCount.bottom
                    right constrainTo forksImage.left
                    right.margin = Gaps.SMALL
                }

                forksImage.apply {
                    top constrainTo forksCount.top
                    right constrainTo parent.right
                    bottom constrainTo forksCount.bottom
                }

                bookmarkToggle.apply {
                    right constrainTo parent.right
                    bottom constrainTo parent.bottom
                }
            }) {
            Text(
                modifier = Modifier.tag(HeaderTags.NAME),
                text = model.name,
                style = typography.h5
            )
            Text(
                modifier = Modifier.tag(HeaderTags.OWNER),
                text = model.ownerName,
                style = typography.subtitle2
            )
            Text(
                modifier = Modifier.tag(HeaderTags.DESCRIPTION),
                text = model.description,
                style = typography.body2
            )
            Text(
                modifier = Modifier.tag(HeaderTags.LAST_UPDATE),
                text = stringResource(
                    R.string.results_updated, model.lastUpdate.format(
                        SIMPLE_ISO_DATE_TIME
                    )),
                style = typography.overline
            )
            Text(
                modifier = Modifier.tag(HeaderTags.OPEN_ISSUES),
                text = stringResource(R.string.details_open_issues, model.openIssuesCount),
                style = typography.overline
            )
            Text(
                modifier = Modifier.tag(HeaderTags.LANGUAGE),
                text = model.language,
                style = typography.overline
            )
            Text(
                modifier = Modifier.tag(HeaderTags.STARS),
                text = model.starsCount.toString(),
                style = typography.body2
            )
            Text(
                modifier = Modifier.tag(HeaderTags.WATCHERS),
                text = model.watchersCount.toString(),
                style = typography.body2
            )
            Text(
                modifier = Modifier.tag(HeaderTags.FORKS),
                text = model.forksCount.toString(),
                style = typography.body2
            )

            val starsImage = vectorResource(id = R.drawable.ic_star)
            Image(
                modifier = Modifier.tag(HeaderTags.STARS_IMAGE),
                painter = VectorPainter(asset = starsImage)
            )

            val watchersImage = vectorResource(id = R.drawable.ic_eye)
            Image(
                modifier = Modifier.tag(HeaderTags.WATCHERS_IMAGE),
                painter = VectorPainter(asset = watchersImage)
            )

            val forksImage = vectorResource(id = R.drawable.ic_split)
            Image(
                modifier = Modifier.tag(HeaderTags.FORKS_IMAGE),
                painter = VectorPainter(asset = forksImage)
            )

            Bookmark(
                modifier = Modifier.tag(HeaderTags.BOOKMARK_TOGGLE),
                state = ToggleableState(model.bookmarked),
                onClick = { onBookmarkClick(model) }
            )
        }
    }
}

@Preview
@Suppress("FunctionNaming")
@Composable
fun RepositoryHeaderPreview() {
    RepositoryHeader(model = SampleData.REPOSITORY)
}

@Suppress("FunctionNaming")
@Composable
fun Bookmark(
    state: ToggleableState,
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Semantics(container = true, mergeAllDescendants = true) {
        Box(modifier, gravity = ContentGravity.Center) {
            TriStateToggleable(
                state = state,
                onClick = onClick,
                enabled = enabled,
                modifier = Modifier.ripple(bounded = false, enabled = enabled)
            ) {
                val bookmarkImage = when (state) {
                    ToggleableState.On, ToggleableState.Indeterminate -> vectorResource(id = R.drawable.ic_bookmark)
                    ToggleableState.Off -> vectorResource(id = R.drawable.ic_bookmark_border)
                }

                Image(painter = VectorPainter(asset = bookmarkImage))
            }
        }
    }
}

private object HeaderTags {
    const val NAME = "name"
    const val OWNER = "owner"
    const val DESCRIPTION = "description"
    const val LAST_UPDATE = "lastUpdate"
    const val OPEN_ISSUES = "openIssues"
    const val LANGUAGE = "language"
    const val STARS = "start"
    const val WATCHERS = "watchers"
    const val FORKS = "forks"
    const val BOOKMARK_TOGGLE = "bookmarkToggle"
    const val STARS_IMAGE = "starsImage"
    const val WATCHERS_IMAGE = "watchersImage"
    const val FORKS_IMAGE = "forksImage"
}
