package io.involvedapps.beefirstonvideo.video.mapping

import io.involvedapps.beefirstonvideo.video.VideoViewModel.GetVideoInfoState.ErrorCodeUI
import io.involvedapps.usescases.video.VideoUsecase.GetVideoInfoResult.ErrorCode

internal fun ErrorCode.mapToUI() = when (this) {
    ErrorCode.NetworkError -> ErrorCodeUI.NetworkError
    ErrorCode.ParsingError -> ErrorCodeUI.ParsingError
    ErrorCode.UnknownError -> ErrorCodeUI.UnknownError
}
