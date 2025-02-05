package io.involvedapps.usescases.video

interface VideoRepository {

    suspend fun getVideoInfo(videoId: String): VideoUsecase.GetVideoInfoResult

}