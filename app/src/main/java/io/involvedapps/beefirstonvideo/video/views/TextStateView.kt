package io.involvedapps.beefirstonvideo.video.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.involvedapps.beefirstonvideo.R
import io.involvedapps.beefirstonvideo.ui.theme.BeeFirstOnVideoTheme

@Composable
internal fun TextStateView(
    modifier: Modifier = Modifier,
    text: String,
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            text = text,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview
@Composable
private fun EmptyStateViewPreview() {
    BeeFirstOnVideoTheme {
        TextStateView(
            text = stringResource(R.string.video_state_empty_label)
        )
    }
}