package io.involvedapps.beefirstonvideo.video.views

import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import io.involvedapps.beefirstonvideo.utils.views.ScreenOrientation

@OptIn(UnstableApi::class)
@Composable
internal fun VideoPlayerView(
    modifier: Modifier = Modifier,
    videoTitle: String,
    exoPlayer: ExoPlayer,
    screenOrientation: ScreenOrientation,
    onVideoResume: () -> Unit,
    onVideoPause: () -> Unit,
    onVideoDispose: () -> Unit,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleEvent = remember { mutableStateOf(Lifecycle.Event.ON_ANY) }
    val lifecycleObserver = remember {
        LifecycleEventObserver { _, event ->
            lifecycleEvent.value = event
        }
    }

    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        //there is some place for a title in portrait mode only
        if (screenOrientation is ScreenOrientation.Portrait) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                text = videoTitle.replaceFirstChar { it.uppercaseChar() },
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = {
                PlayerView(context).apply {
                    player = exoPlayer
                    hideController()
                }
            },
        )
    }

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)
    }
    when (lifecycleEvent.value) {
        Lifecycle.Event.ON_RESUME -> onVideoResume()
        Lifecycle.Event.ON_STOP -> onVideoPause()
        Lifecycle.Event.ON_DESTROY -> {
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
            onVideoDispose()
        }
        Lifecycle.Event.ON_CREATE,
        Lifecycle.Event.ON_PAUSE,
        Lifecycle.Event.ON_START,
        Lifecycle.Event.ON_ANY -> {
            //do nothing
        }
    }
}