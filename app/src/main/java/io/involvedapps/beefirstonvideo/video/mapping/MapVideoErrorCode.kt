package io.involvedapps.beefirstonvideo.video.mapping

import io.involvedapps.beefirstonvideo.video.VideoViewModel.VideoState.VideoInfoErrorCodeUI
import io.involvedapps.usescases.video.VideoUsecase.GetVideoInfoResult.ErrorCode

internal fun ErrorCode.mapToUI() = when (this) {
    ErrorCode.NetworkError -> VideoInfoErrorCodeUI.NetworkError
    ErrorCode.ParsingError -> VideoInfoErrorCodeUI.ParsingError
    ErrorCode.UnknownError -> VideoInfoErrorCodeUI.UnknownError
    ErrorCode.BadVideoId -> VideoInfoErrorCodeUI.BadVideoId
}
