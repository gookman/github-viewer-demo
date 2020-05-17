package dk.bluebox.demo.githubviewer.common.ui.compose

import android.util.Log
import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.layout.Arrangement
import androidx.ui.layout.Column
import androidx.ui.layout.fillMaxSize
import androidx.ui.layout.padding
import androidx.ui.material.Button
import androidx.ui.material.MaterialTheme
import androidx.ui.res.stringResource
import androidx.ui.tooling.preview.Preview
import dk.bluebox.demo.githubviewer.R

@Composable
fun ErrorScreen(onRetry: () -> Unit) {
    val typography = MaterialTheme.typography

    Log.d("HELLO_ERROR", "We have error")

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalGravity = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(bottom = Gaps.DEFAULT),
            text = stringResource(id = R.string.general_error),
            style = typography.h5
        )
        Button(onClick = onRetry) {
            Text(
                text = stringResource(id = R.string.general_retry),
                style = typography.subtitle1
            )
        }
    }
}

@Preview
@Composable
private fun ErrorScreen() {
    ErrorScreen(onRetry = {})
}
