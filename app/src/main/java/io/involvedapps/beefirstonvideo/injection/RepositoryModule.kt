package io.involvedapps.beefirstonvideo.injection

import io.involvedapps.usescases.video.VideoRepository
import io.involvedapps.usescases.video.VideoUsecase
import io.involvedapps.usescases.video.models.VideoInfo
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::VideoRepositoryFake) { bind<VideoRepository>() }
}

class VideoRepositoryFake : VideoRepository {
    override suspend fun getVideoInfo(videoId: String): VideoUsecase.GetVideoInfoResult {
        return VideoUsecase.GetVideoInfoResult.Success(
            videoInfo = VideoInfo(
                title = "J'ai escaladé une tour de 210 mètres à mains nues !",
                thumbnailUrl = "https://s2.dmcdn.net/v/Xmxzi1ddBIE0327ja/x360",
                videoUrl = "https://www.dailymotion.com/cdn/manifest/video/x9d9k6k.m3u8?sec=Ta27UcyLm5E2yETMcDW2yZdroLJ3QS6O31DJBU2ySrZ9bBEt6Ls_GdvZ58ZPrVTXGvE3JhqK4T7klSf7TzuF_g&dmTs=289765&dmV1st=a4523e3c-5ada-1ff8-d31c-cd10ae8b0aff",
            )
        )
    }
}