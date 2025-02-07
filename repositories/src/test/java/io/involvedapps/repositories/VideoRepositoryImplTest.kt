package io.involvedapps.repositories

import io.involvedapps.repositories.video.VideoRepositoryImpl
import io.involvedapps.repositories.video.VideoRepositoryRemote
import io.involvedapps.repositories.video.models.VideoInfoEntity
import io.involvedapps.repositories.video.models.nextVideoInfoEntity
import io.involvedapps.usescases.video.VideoUsecase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.random.Random

class VideoRepositoryImplTest {

    private val remoteVideoRepository = mockk<VideoRepositoryRemote>()
    private val videoRepositoryImpl = VideoRepositoryImpl(remoteVideoRepository)

    @Test
    fun repository_getVideoInfo_should_return_Error_with_ErrorCode_NetworkError_if_the_remote_repository_returns_it() = runTest {
        val fakeId = "1"
        coEvery { remoteVideoRepository.getVideoInfo(fakeId) } returns VideoRepositoryRemote.GetVideoInfoRemoteResult.HttpError

        val result = videoRepositoryImpl.getVideoInfo(fakeId)

        assert(result is VideoUsecase.GetVideoInfoResult.Error)
        assert((result as VideoUsecase.GetVideoInfoResult.Error).code is VideoUsecase.GetVideoInfoResult.ErrorCode.NetworkError)
    }

    @Test
    fun repository_getVideoInfo_should_return_Error_with_ErrorCode_ParsingError_if_the_remote_repository_returns_it() = runTest {
        val fakeId = "1"
        coEvery { remoteVideoRepository.getVideoInfo(fakeId) } returns VideoRepositoryRemote.GetVideoInfoRemoteResult.ParsingJSONError

        val result = videoRepositoryImpl.getVideoInfo(fakeId)

        assert(result is VideoUsecase.GetVideoInfoResult.Error)
        assert((result as VideoUsecase.GetVideoInfoResult.Error).code is VideoUsecase.GetVideoInfoResult.ErrorCode.ParsingError)
    }

    @Test
    fun repository_getVideoInfo_should_return_Error_with_ErrorCode_UnknownError_if_the_remote_repository_returns_it() = runTest {
        val fakeId = "1"
        coEvery { remoteVideoRepository.getVideoInfo(fakeId) } returns VideoRepositoryRemote.GetVideoInfoRemoteResult.UnknownError

        val result = videoRepositoryImpl.getVideoInfo(fakeId)

        assert(result is VideoUsecase.GetVideoInfoResult.Error)
        assert((result as VideoUsecase.GetVideoInfoResult.Error).code is VideoUsecase.GetVideoInfoResult.ErrorCode.UnknownError)
    }

    @Test
    fun repository_getVideoInfo_should_return_Error_with_ErrorCode_ParsingError_if_the_remote_repository_returns_bad_videoInfoEntity() = runTest {
        val fakeId = "1"
        val fakeVideoInfoEntity = VideoInfoEntity(
            title = "test title",
            thumbnails = VideoInfoEntity.ThumbnailsEntity(
                thumbnail360Url = "testUrlThumbnails",
            ),
            qualities = VideoInfoEntity.QualitiesEntity(
                qualities = listOf()
            ),
        )
        coEvery { remoteVideoRepository.getVideoInfo(fakeId) } returns VideoRepositoryRemote.GetVideoInfoRemoteResult.Success(
            videoInfo = fakeVideoInfoEntity
        )

        val result = videoRepositoryImpl.getVideoInfo(fakeId)

        assert(result is VideoUsecase.GetVideoInfoResult.Error)
        assert((result as VideoUsecase.GetVideoInfoResult.Error).code is VideoUsecase.GetVideoInfoResult.ErrorCode.ParsingError)
    }

    @Test
    fun repository_getVideoInfo_should_return_Success_with_videoInfo_if_the_remote_repository_returns_it() = runTest {
        val fakeId = "1"
        val fakeVideoInfoEntity = Random.nextVideoInfoEntity()
        coEvery { remoteVideoRepository.getVideoInfo(fakeId) } returns VideoRepositoryRemote.GetVideoInfoRemoteResult.Success(
            videoInfo = fakeVideoInfoEntity
        )

        val result = videoRepositoryImpl.getVideoInfo(fakeId)

        assert(result is VideoUsecase.GetVideoInfoResult.Success)
        val successResult = (result as VideoUsecase.GetVideoInfoResult.Success).videoInfo
        assert(successResult.title == fakeVideoInfoEntity.title)
        assert(successResult.thumbnailUrl == fakeVideoInfoEntity.thumbnails.thumbnail360Url)
        assert(successResult.videoUrl == fakeVideoInfoEntity.qualities.qualities.first().url)
    }

}