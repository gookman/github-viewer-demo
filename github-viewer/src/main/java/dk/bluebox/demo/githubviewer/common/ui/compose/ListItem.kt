package dk.bluebox.demo.githubviewer.common.ui.compose

data class ListItem<T>(val value: T, val index: Int)

fun <T> List<T>.wrapInListItem(): List<ListItem<T>> {
    return mapIndexed { index, item -> ListItem(item, index) }
}
