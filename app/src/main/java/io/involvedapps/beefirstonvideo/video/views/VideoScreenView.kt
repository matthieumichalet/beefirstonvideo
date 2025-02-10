package io.involvedapps.beefirstonvideo.video.views

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import io.involvedapps.beefirstonvideo.utils.views.ScreenOrientation
import io.involvedapps.beefirstonvideo.utils.views.getScreenOrientation
import io.involvedapps.beefirstonvideo.video.VideoViewModel
import io.involvedapps.beefirstonvideo.video.models.VideoInfoUI

@Composable
internal fun VideoScreenView(
    modifier: Modifier = Modifier,
    state: VideoViewModel.VideoState,
    searchedText: String,
    onSearchedTextChange: (String) -> Unit,
    onClickSearch: (String) -> Unit,
    onInitVideo: (Context, VideoInfoUI) -> Unit,
    onVideoResume: () -> Unit,
    onVideoPause: () -> Unit,
    onVideoDispose: () -> Unit,
) {
    val configuration = LocalConfiguration.current
    val orientation by remember { mutableStateOf(getScreenOrientation(configuration)) }

    when (orientation) {
        ScreenOrientation.Landscape -> {
            VideoScreenViewLandscape(
                modifier = modifier,
                state = state,
                searchedText = searchedText,
                onSearchedTextChange = onSearchedTextChange,
                onClickSearch = onClickSearch,
                onInitVideo = onInitVideo,
                onVideoResume = onVideoResume,
                onVideoPause = onVideoPause,
                onVideoDispose = onVideoDispose,
            )
        }
        ScreenOrientation.Portrait -> {
            VideoScreenViewPortrait(
                modifier = modifier,
                state = state,
                searchedText = searchedText,
                onSearchedTextChange = onSearchedTextChange,
                onClickSearch = onClickSearch,
                onInitVideo = onInitVideo,
                onVideoResume = onVideoResume,
                onVideoPause = onVideoPause,
                onVideoDispose = onVideoDispose,
            )
        }
    }


}