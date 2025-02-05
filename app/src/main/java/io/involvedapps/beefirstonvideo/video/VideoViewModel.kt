package io.involvedapps.beefirstonvideo.video

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.involvedapps.beefirstonvideo.video.mapping.mapToUI
import io.involvedapps.beefirstonvideo.video.models.VideoInfoUI
import io.involvedapps.usescases.video.VideoUsecase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VideoViewModel(
    private val videoUsecase: VideoUsecase,
): ViewModel() {

    private val _getVideoInfoState = MutableStateFlow<GetVideoInfoState>(GetVideoInfoState.None)
    val getVideoInfoState: StateFlow<GetVideoInfoState> = _getVideoInfoState

    fun getVideoInfoById(videoId: String) {
        viewModelScope.launch {
            _getVideoInfoState.value = GetVideoInfoState.Loading
            when (val usecaseResult = videoUsecase.getVideoInfoById(videoId)) {
                is VideoUsecase.GetVideoInfoResult.Error -> {
                    _getVideoInfoState.value = GetVideoInfoState.Error(
                        code = usecaseResult.code.mapToUI()
                    )
                }
                is VideoUsecase.GetVideoInfoResult.Success -> {
                    _getVideoInfoState.value = GetVideoInfoState.Success(
                        videoInfo = usecaseResult.videoInfo.mapToUI()
                    )
                }
            }
        }
    }

    sealed interface GetVideoInfoState {

        data object None : GetVideoInfoState
        data object Loading : GetVideoInfoState
        data class Error(val code: ErrorCodeUI) : GetVideoInfoState
        data class Success(val videoInfo: VideoInfoUI) : GetVideoInfoState

        sealed interface ErrorCodeUI {

            data object NetworkError : ErrorCodeUI
            data object ParsingError : ErrorCodeUI
            data object UnknownError : ErrorCodeUI

        }
    }

}