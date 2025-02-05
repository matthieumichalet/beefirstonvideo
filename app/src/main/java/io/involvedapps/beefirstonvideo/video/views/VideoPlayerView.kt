package io.involvedapps.beefirstonvideo.video.views

import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player.REPEAT_MODE_OFF
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.ui.PlayerView
import io.involvedapps.beefirstonvideo.video.models.VideoInfoUI

@OptIn(UnstableApi::class)
@Composable
internal fun VideoPlayerView(
    modifier: Modifier = Modifier,
    videoInfo: VideoInfoUI,
) {
    val context = LocalContext.current

    val playbackPosition by remember { mutableLongStateOf(0L) }
    val exoPlayer by remember { mutableStateOf(ExoPlayer.Builder(context).build()) }

    val dataSourceFactory = DefaultDataSource.Factory(context, DefaultHttpDataSource.Factory())
    val mediaItem = MediaItem.fromUri(videoInfo.videoUrl)
    val mediaSource = HlsMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem)

    exoPlayer.setMediaSource(mediaSource)
    exoPlayer.seekTo(playbackPosition)
    exoPlayer.prepare()
    exoPlayer.playWhenReady = false
    exoPlayer.repeatMode = REPEAT_MODE_OFF

    AndroidView(factory = {
        PlayerView(context).apply {
            player = exoPlayer
        }
    })
    DisposableEffect(Unit) {
        onDispose { exoPlayer.release() }
    }
}