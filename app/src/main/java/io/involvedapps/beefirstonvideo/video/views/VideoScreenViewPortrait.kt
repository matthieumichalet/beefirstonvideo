package io.involvedapps.beefirstonvideo.video.views

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.involvedapps.beefirstonvideo.utils.views.ScreenOrientation
import io.involvedapps.beefirstonvideo.video.VideoViewModel
import io.involvedapps.beefirstonvideo.video.models.VideoInfoUI

@Composable
internal fun VideoScreenViewPortrait(
    modifier: Modifier = Modifier,
    searchedText: String,
    onSearchedTextChange: (String) -> Unit,
    onClickSearch: (String) -> Unit,
    state: VideoViewModel.VideoState,
    onInitVideo: (Context, VideoInfoUI) -> Unit,
    onVideoResume: () -> Unit,
    onVideoPause: () -> Unit,
    onVideoDispose: () -> Unit,
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        VideoIdTextFieldView(
            text = searchedText,
            onSearchedTextChange = onSearchedTextChange,
            onClickSearch = onClickSearch,
        )

        VideoStateView(
            state = state,
            screenOrientation = ScreenOrientation.Portrait,
            onInitVideo = onInitVideo,
            onVideoResume = onVideoResume,
            onVideoPause = onVideoPause,
            onVideoDispose = onVideoDispose,
        )
    }
}