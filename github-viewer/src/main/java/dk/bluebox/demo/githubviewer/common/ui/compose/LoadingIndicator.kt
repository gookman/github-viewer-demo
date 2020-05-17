package dk.bluebox.demo.githubviewer.common.ui.compose

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.ContentGravity
import androidx.ui.layout.fillMaxSize
import androidx.ui.material.CircularProgressIndicator
import androidx.ui.tooling.preview.Preview

@Preview
@Suppress("FunctionNaming")
@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        gravity = ContentGravity(0F, 0F)
    ) {
        CircularProgressIndicator()
    }
}
