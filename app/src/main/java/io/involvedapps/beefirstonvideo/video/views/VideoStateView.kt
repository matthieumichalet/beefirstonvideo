package io.involvedapps.beefirstonvideo.video.views

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import io.involvedapps.beefirstonvideo.R
import io.involvedapps.beefirstonvideo.utils.views.ScreenOrientation
import io.involvedapps.beefirstonvideo.video.VideoViewModel
import io.involvedapps.beefirstonvideo.video.models.VideoInfoUI

@Composable
internal fun VideoStateView(
    state: VideoViewModel.VideoState,
    screenOrientation: ScreenOrientation,
    onInitVideo: (Context, VideoInfoUI) -> Unit,
    onVideoResume: () -> Unit,
    onVideoPause: () -> Unit,
    onVideoDispose: () -> Unit,
) {
    val context = LocalContext.current

    when (state) {
        is VideoViewModel.VideoState.WaitingInitialization -> {
            onInitVideo(context, state.videoInfo)
            TextStateView(text = stringResource(R.string.video_state_loading_label))
        }
        is VideoViewModel.VideoState.VideoInfoError -> {
            val textErrorID = when (state.code) {
                VideoViewModel.VideoState.VideoInfoErrorCodeUI.NetworkError -> {
                    R.string.video_state_error_label_no_network
                }
                VideoViewModel.VideoState.VideoInfoErrorCodeUI.ParsingError -> {
                    R.string.video_state_info_error_label
                }
                VideoViewModel.VideoState.VideoInfoErrorCodeUI.UnknownError -> {
                    R.string.video_state_error_label_unknown
                }
                VideoViewModel.VideoState.VideoInfoErrorCodeUI.BadVideoId -> {
                    R.string.video_state_error_label_bad_video_id
                }
            }
            TextStateView(
                text = stringResource(R.string.video_state_error_label) + stringResource(textErrorID)
            )
        }
        VideoViewModel.VideoState.Loading -> {
            TextStateView(text = stringResource(R.string.video_state_loading_label))
        }
        VideoViewModel.VideoState.None -> {
            TextStateView(text = stringResource(R.string.video_state_empty_label))
        }

        is VideoViewModel.VideoState.VideoError -> {
            val textErrorID = when (state.code) {
                VideoViewModel.VideoState.VideoErrorCodeUI.ExoPlayerBadVideoId -> {
                    R.string.video_state_error_label_bad_video_id
                }
                VideoViewModel.VideoState.VideoErrorCodeUI.ExoPlayerNoNetwork -> {
                    R.string.video_state_error_label_no_network
                }
                VideoViewModel.VideoState.VideoErrorCodeUI.ExoPlayerNoNetworkPermission -> {
                    R.string.video_state_error_label_no_network_permission
                }
                VideoViewModel.VideoState.VideoErrorCodeUI.UnknownError -> {
                    R.string.video_state_error_label_unknown
                }
            }
            TextStateView(
                text = stringResource(R.string.video_state_error_label) + stringResource(textErrorID)
            )
        }
        is VideoViewModel.VideoState.VideoReady -> {
            VideoPlayerView(
                videoTitle = state.videoInfo.title,
                exoPlayer = state.exoPlayer,
                screenOrientation = screenOrientation,
                onVideoResume = onVideoResume,
                onVideoPause = onVideoPause,
                onVideoDispose = onVideoDispose,
            )
        }
    }
}