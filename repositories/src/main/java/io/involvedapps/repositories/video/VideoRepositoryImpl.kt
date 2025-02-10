package io.involvedapps.repositories.video

import io.involvedapps.repositories.video.mapping.mapToDomain
import io.involvedapps.usescases.video.VideoRepository
import io.involvedapps.usescases.video.VideoUsecase

class VideoRepositoryImpl(
    private val remoteRepository: VideoRepositoryRemote,
) : VideoRepository {

    override suspend fun getVideoInfo(videoId: String): VideoUsecase.GetVideoInfoResult {
        return when (val result = remoteRepository.getVideoInfo(videoId)) {
            VideoRepositoryRemote.GetVideoInfoRemoteResult.BadVideoId -> {
                VideoUsecase.GetVideoInfoResult.Error(
                    code = VideoUsecase.GetVideoInfoResult.ErrorCode.BadVideoId
                )
            }
            VideoRepositoryRemote.GetVideoInfoRemoteResult.HttpError -> {
                VideoUsecase.GetVideoInfoResult.Error(
                    code = VideoUsecase.GetVideoInfoResult.ErrorCode.NetworkError
                )
            }
            VideoRepositoryRemote.GetVideoInfoRemoteResult.ParsingJSONError -> {
                VideoUsecase.GetVideoInfoResult.Error(
                    code = VideoUsecase.GetVideoInfoResult.ErrorCode.ParsingError
                )
            }
            VideoRepositoryRemote.GetVideoInfoRemoteResult.UnknownError -> {
                VideoUsecase.GetVideoInfoResult.Error(
                    code = VideoUsecase.GetVideoInfoResult.ErrorCode.UnknownError
                )
            }
            is VideoRepositoryRemote.GetVideoInfoRemoteResult.Success -> {
                val videoInfo = result.videoInfo.mapToDomain()

                if (videoInfo == null) {
                    VideoUsecase.GetVideoInfoResult.Error(
                        code = VideoUsecase.GetVideoInfoResult.ErrorCode.ParsingError
                    )
                } else {
                    VideoUsecase.GetVideoInfoResult.Success(
                        videoInfo = videoInfo
                    )
                }
            }
        }
    }

}