package io.involvedapps.beefirstonvideo.video.views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

@Composable
internal fun VideoPlayerView(
    modifier: Modifier = Modifier,
    exoPlayer: ExoPlayer,
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

    AndroidView(
        modifier = modifier,
        factory = {
            PlayerView(context).apply {
                player = exoPlayer
            }
        },
    )

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