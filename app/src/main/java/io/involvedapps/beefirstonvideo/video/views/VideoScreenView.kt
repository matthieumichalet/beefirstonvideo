package io.involvedapps.beefirstonvideo.video.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.involvedapps.beefirstonvideo.video.VideoViewModel

@Composable
internal fun VideoScreenView(
    modifier: Modifier = Modifier,
    state: VideoViewModel.GetVideoInfoState,
    onClickSearch: (String) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        VideoIdTextFieldView(
            onClickSearch = onClickSearch,
        )

        when (state) {
            is VideoViewModel.GetVideoInfoState.Success -> {
                VideoPlayerView(
                    videoInfo = state.videoInfo
                )
            }
            is VideoViewModel.GetVideoInfoState.Error -> {
                Text("Error")
            }
            VideoViewModel.GetVideoInfoState.Loading -> {
                Text("Loading")
            }
            VideoViewModel.GetVideoInfoState.None -> {
                Text("None")
            }
        }
    }

}