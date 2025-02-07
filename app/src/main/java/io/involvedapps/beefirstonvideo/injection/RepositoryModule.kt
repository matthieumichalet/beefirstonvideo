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
                videoUrl = "https://www.dailymotion.com/cdn/manifest/video/x9cgw2w.m3u8?sec=cyP0O10frsgrdjdgbDL7q8N6UwOqU243xNeciGoXpSEHnAaFwmkvvH5ppK_wBMh7CCSiZVEG6-vqCNmxN9U4kA&dmTs=289765&dmV1st=a4523e3c-5ada-1ff8-d31c-cd10ae8b0aff",
            )
        )
    }
}