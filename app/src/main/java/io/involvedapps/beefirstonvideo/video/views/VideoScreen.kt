package io.involvedapps.beefirstonvideo.video.views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import io.involvedapps.beefirstonvideo.video.VideoViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun VideoScreen(
    modifier: Modifier = Modifier,
    videoViewModel: VideoViewModel = koinViewModel(),
) {
    val state = videoViewModel.getVideoInfoState.collectAsState().value

    VideoScreenView(
        modifier = modifier,
        state = state,
        onClickSearch = videoViewModel::getVideoInfoById,
    )

}