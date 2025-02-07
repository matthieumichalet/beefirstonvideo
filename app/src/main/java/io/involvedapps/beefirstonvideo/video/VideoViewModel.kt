package io.involvedapps.beefirstonvideo.video

import android.content.Context
import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.Player.REPEAT_MODE_OFF
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import io.involvedapps.beefirstonvideo.video.mapping.mapToUI
import io.involvedapps.beefirstonvideo.video.models.VideoInfoUI
import io.involvedapps.usescases.video.VideoUsecase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VideoViewModel(
    private val videoUsecase: VideoUsecase,
): ViewModel() {

    private val _videoState = MutableStateFlow<VideoState>(VideoState.None)
    val videoState: StateFlow<VideoState> = _videoState

    private val _searchedTextState = MutableStateFlow("")
    val searchedTextState: StateFlow<String> = _searchedTextState

    private var playbackPosition : Long = 0L
    private val exoPlayer = MutableStateFlow<ExoPlayer?>(null)

    fun updateSearchedText(text: String) {
        _searchedTextState.value = text
    }

    fun getVideoById(videoId: String) {
        viewModelScope.launch {
            onVideoDispose()
            _videoState.value = VideoState.Loading
            when (val usecaseResult = videoUsecase.getVideoInfoById(videoId)) {
                is VideoUsecase.GetVideoInfoResult.Error -> {
                    _videoState.value = VideoState.VideoInfoError(
                        code = usecaseResult.code.mapToUI()
                    )
                }
                is VideoUsecase.GetVideoInfoResult.Success -> {
                    _videoState.value = VideoState.WaitingInitialization(
                        videoInfo = usecaseResult.videoInfo.mapToUI()
                    )
                }
            }
        }
    }

    @OptIn(UnstableApi::class)
    fun initializePlayer(context: Context, videoUrl: String) {
        if (exoPlayer.value != null) {
            return
        }

        viewModelScope.launch {
            try {
                exoPlayer.value = ExoPlayer.Builder(context).build()
                val dataSourceFactory =
                    DefaultDataSource.Factory(context, DefaultHttpDataSource.Factory())
                val mediaItem = MediaItem.fromUri(videoUrl)
                val mediaSource = HlsMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem)
                exoPlayer.value?.let {
                    it.apply {
                        setMediaSource(mediaSource)
                        seekTo(playbackPosition)
                        addListener(object: Player.Listener {
                            override fun onPlaybackStateChanged(playbackState: Int) {
                                if (playbackState == Player.STATE_READY) {
                                    _videoState.value = VideoState.VideoReady(it)
                                }
                            }
                            override fun onPlayerError(error: PlaybackException) {
                                handlePlayerError(error)
                            }
                        })
                        playWhenReady = false
                        repeatMode = REPEAT_MODE_OFF
                        prepare()
                    }
                }
            } catch (exception : Exception) {
                handlePlayerError(exception)
                _videoState.value = VideoState.VideoError(VideoState.VideoErrorCodeUI.UnknownError)
            }
        }
    }

    private fun handlePlayerError(error: Exception) {
        when (error) {
            is PlaybackException -> {
                when (error.errorCode) {
                    PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_FAILED,
                    PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_TIMEOUT -> {
                        _videoState.value = VideoState.VideoError(VideoState.VideoErrorCodeUI.ExoPlayerNoNetwork)
                    }
                    PlaybackException.ERROR_CODE_IO_INVALID_HTTP_CONTENT_TYPE,
                    PlaybackException.ERROR_CODE_IO_FILE_NOT_FOUND -> {
                        _videoState.value = VideoState.VideoError(VideoState.VideoErrorCodeUI.ExoPlayerBadVideoId)
                    }
                    PlaybackException.ERROR_CODE_IO_UNSPECIFIED -> {
                        _videoState.value = VideoState.VideoError(VideoState.VideoErrorCodeUI.ExoPlayerNoNetworkPermission)
                    }
                    else -> {
                        _videoState.value = VideoState.VideoError(VideoState.VideoErrorCodeUI.UnknownError)
                    }
                }
            }
            else -> {
                _videoState.value = VideoState.VideoError(VideoState.VideoErrorCodeUI.UnknownError)
            }
        }
    }

    fun onVideoResume() {
        exoPlayer.value?.play()
    }

    fun onVideoPause() {
        exoPlayer.value?.pause()
    }

    fun onVideoDispose() {
        _videoState.value = VideoState.None
        exoPlayer.value?.release()
        exoPlayer.value = null
    }

    sealed interface VideoState {

        data object None : VideoState
        data object Loading : VideoState
        data class VideoInfoError(val code: VideoInfoErrorCodeUI) : VideoState
        data class WaitingInitialization(val videoInfo: VideoInfoUI) : VideoState
        data class VideoReady(val exoPlayer: ExoPlayer) : VideoState
        data class VideoError(val code: VideoErrorCodeUI) : VideoState

        sealed interface VideoInfoErrorCodeUI {

            data object NetworkError : VideoInfoErrorCodeUI
            data object ParsingError : VideoInfoErrorCodeUI
            data object UnknownError : VideoInfoErrorCodeUI

        }

        sealed interface VideoErrorCodeUI {

            data object ExoPlayerNoNetworkPermission : VideoErrorCodeUI
            data object ExoPlayerNoNetwork : VideoErrorCodeUI
            data object ExoPlayerBadVideoId : VideoErrorCodeUI
            data object UnknownError : VideoErrorCodeUI

        }
    }

}