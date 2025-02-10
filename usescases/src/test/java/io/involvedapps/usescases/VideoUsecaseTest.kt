package io.involvedapps.usescases

import io.involvedapps.usescases.video.VideoRepository
import io.involvedapps.usescases.video.VideoUsecase
import io.involvedapps.usescases.video.models.nextVideoInfo
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.random.Random

class VideoUsecaseTest {

    private val videoRepository = mockk<VideoRepository>()
    private val videoUsecase = VideoUsecase(videoRepository)

    @Test
    fun usecase_getVideoInfoById_should_return_Error_with_ErrorCode_BadVideoId_if_the_repository_returns_it() = runTest {
        val fakeId = "1"
        coEvery { videoRepository.getVideoInfo(fakeId) } returns VideoUsecase.GetVideoInfoResult.Error(
            VideoUsecase.GetVideoInfoResult.ErrorCode.BadVideoId
        )

        val result = videoUsecase.getVideoInfoById(fakeId)

        assert(result is VideoUsecase.GetVideoInfoResult.Error)
        assert((result as VideoUsecase.GetVideoInfoResult.Error).code is VideoUsecase.GetVideoInfoResult.ErrorCode.BadVideoId)
    }

    @Test
    fun usecase_getVideoInfoById_should_return_Error_with_ErrorCode_NetworkError_if_the_repository_returns_it() = runTest {
        val fakeId = "1"
        coEvery { videoRepository.getVideoInfo(fakeId) } returns VideoUsecase.GetVideoInfoResult.Error(
            VideoUsecase.GetVideoInfoResult.ErrorCode.NetworkError
        )

        val result = videoUsecase.getVideoInfoById(fakeId)

        assert(result is VideoUsecase.GetVideoInfoResult.Error)
        assert((result as VideoUsecase.GetVideoInfoResult.Error).code is VideoUsecase.GetVideoInfoResult.ErrorCode.NetworkError)
    }

    @Test
    fun usecase_getVideoInfoById_should_return_Error_with_ParsingError_if_the_repository_returns_it() = runTest {
        val fakeId = "1"
        coEvery { videoRepository.getVideoInfo(fakeId) } returns VideoUsecase.GetVideoInfoResult.Error(
            VideoUsecase.GetVideoInfoResult.ErrorCode.ParsingError
        )

        val result = videoUsecase.getVideoInfoById(fakeId)

        assert(result is VideoUsecase.GetVideoInfoResult.Error)
        assert((result as VideoUsecase.GetVideoInfoResult.Error).code is VideoUsecase.GetVideoInfoResult.ErrorCode.ParsingError)
    }

    @Test
    fun usecase_getVideoInfoById_should_return_Error_with_UnknownError_if_the_repository_returns_it() = runTest {
        val fakeId = "1"
        coEvery { videoRepository.getVideoInfo(fakeId) } returns VideoUsecase.GetVideoInfoResult.Error(
            VideoUsecase.GetVideoInfoResult.ErrorCode.UnknownError
        )

        val result = videoUsecase.getVideoInfoById(fakeId)

        assert(result is VideoUsecase.GetVideoInfoResult.Error)
        assert((result as VideoUsecase.GetVideoInfoResult.Error).code is VideoUsecase.GetVideoInfoResult.ErrorCode.UnknownError)
    }

    @Test
    fun usecase_getVideoInfoById_should_return_Success_with_VideoInfo_if_the_repository_returns_it() = runTest {
        val fakeId = "1"
        val fakeVideoInfo = Random.nextVideoInfo()
        coEvery { videoRepository.getVideoInfo(fakeId) } returns VideoUsecase.GetVideoInfoResult.Success(
            videoInfo = fakeVideoInfo
        )

        val result = videoUsecase.getVideoInfoById(fakeId)

        assert(result is VideoUsecase.GetVideoInfoResult.Success)

        val successResult = (result as VideoUsecase.GetVideoInfoResult.Success).videoInfo
        assert(successResult.title == fakeVideoInfo.title)
        assert(successResult.thumbnailUrl == fakeVideoInfo.thumbnailUrl)
        assert(successResult.videoUrl == fakeVideoInfo.videoUrl)
    }

}