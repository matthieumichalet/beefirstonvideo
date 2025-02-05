package io.involvedapps.usescases.video

import io.involvedapps.usescases.video.models.VideoInfo

class VideoUsecase(
    private val videoRepository: VideoRepository,
) {

    suspend fun getVideoInfoById(videoId: String): GetVideoInfoResult {
        return videoRepository.getVideoInfo(videoId)
    }

    sealed interface GetVideoInfoResult {

        data class Error(val code: ErrorCode) : GetVideoInfoResult
        data class Success(val videoInfo: VideoInfo) : GetVideoInfoResult

        sealed interface ErrorCode {

            data object NetworkError : ErrorCode
            data object ParsingError : ErrorCode
            data object UnknownError : ErrorCode

        }
    }

}