package io.involvedapps.beefirstonvideo.video.views

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import io.involvedapps.beefirstonvideo.video.VideoViewModel

@Composable
internal fun VideoScreenView(
    modifier: Modifier = Modifier,
    state: VideoViewModel.VideoState,
    onClickSearch: (String) -> Unit,
    onInitVideo: (Context, String) -> Unit,
    onVideoResume: () -> Unit,
    onVideoPause: () -> Unit,
    onVideoDispose: () -> Unit,
) {
    val context = LocalContext.current

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        VideoIdTextFieldView(
            onClickSearch = onClickSearch,
        )

        when (state) {
            is VideoViewModel.VideoState.WaitingInitialization -> {
                LaunchedEffect(Unit) {
                    onInitVideo(context, state.videoInfo.videoUrl)
                }
                Text("Waiting")
            }
            is VideoViewModel.VideoState.VideoInfoError -> {
                Text("Error")
            }
            VideoViewModel.VideoState.Loading -> {
                Text("Loading")
            }
            VideoViewModel.VideoState.None -> {
                Text("None")
            }

            is VideoViewModel.VideoState.VideoError -> {
                Text("Error ${state.code}")
            }
            is VideoViewModel.VideoState.VideoReady -> {
                VideoPlayerView(
                    exoPlayer = state.exoPlayer,
                    onVideoResume = onVideoResume,
                    onVideoPause = onVideoPause,
                    onVideoDispose = onVideoDispose,
                )
            }
        }
    }

}