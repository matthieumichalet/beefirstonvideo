package io.involvedapps.beefirstonvideo.video

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun VideoScreen(
    modifier: Modifier = Modifier,
    videoViewModel: VideoViewModel = koinViewModel(),
) {
    val state = videoViewModel.getVideoInfoState.collectAsState().value

    when (state) {
        is VideoViewModel.GetVideoInfoState.Error -> {
            Text("ERROR")
        }
        VideoViewModel.GetVideoInfoState.Loading -> {
            Text("LOADING")
        }
        VideoViewModel.GetVideoInfoState.None -> {
            Text("NONE")
        }
        is VideoViewModel.GetVideoInfoState.Success -> {
            Text("SUCCESS video url = ${state.videoInfo.videoUrl}")
        }
    }

    LaunchedEffect(Unit) {
        videoViewModel.getVideoInfoById("blabla")
    }
}