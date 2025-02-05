package io.involvedapps.usescases.video

import io.involvedapps.usescases.video.models.VideoInfo

interface VideoRepository {

    suspend fun getVideoInfo(videoId: String): VideoUsecase.GetVideoInfoResult

}