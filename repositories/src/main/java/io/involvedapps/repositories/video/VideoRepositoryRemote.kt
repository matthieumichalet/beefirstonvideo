package io.involvedapps.repositories.video

import android.net.Uri
import io.involvedapps.repositories.video.models.VideoInfoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class VideoRepositoryRemote {

    private val jsonDecoder = Json { ignoreUnknownKeys = true }

    suspend fun getVideoInfo(videoId: String) : GetVideoInfoRemoteResult {
        val urlPath = buildUrlPath(videoId)

        return try {
            val response = doHttpRequest(urlPath)
            val videoInfoEntity = jsonDecoder.decodeFromString<VideoInfoEntity>(response)

            GetVideoInfoRemoteResult.Success(videoInfoEntity)
        } catch (exception : Exception) {
            exception.printStackTrace()
            when (exception) {
                is IOException -> {
                    GetVideoInfoRemoteResult.HttpError
                }
                is SerializationException -> {
                    GetVideoInfoRemoteResult.ParsingJSONError
                }
                else -> {
                    GetVideoInfoRemoteResult.UnknownError
                }
            }
        }
    }

    private suspend fun doHttpRequest(urlPath: String): String {
        try {
            //SETUP CONNECTION
            val url = URL(urlPath)
            val connection = withContext(Dispatchers.IO) {
                url.openConnection()
            } as HttpURLConnection
            connection.requestMethod = "GET"
            connection.setRequestProperty("Accept", "application/json")

            //READ RESPONSE
            val inputStream = withContext(Dispatchers.IO) { connection.inputStream }
            val reader = BufferedReader(InputStreamReader(inputStream))
            val response = StringBuilder()
            var line: String?
            while (withContext(Dispatchers.IO) {
                    reader.readLine()
                }.also { line = it } != null) {
                response.append(line)
            }

            //CLOSE CONNECTION
            withContext(Dispatchers.IO) {
                reader.close()
            }
            connection.disconnect()

            return response.toString()
        } catch (e: Exception) {
            throw e
        }
    }

    private fun buildUrlPath(videoId: String): String {
        val builder = Uri.Builder()
        builder.scheme(URL_SCHEME)
        builder.authority(URL_AUTHORITY)
        builder.path(URL_PATH)

        builder.appendPath(videoId)

        return builder.build().toString()
    }

    companion object {

        private const val URL_SCHEME = "https"
        private const val URL_AUTHORITY = "www.dailymotion.com"
        private const val URL_PATH = "/player/metadata/video/"

    }

    sealed interface GetVideoInfoRemoteResult {

        data object HttpError : GetVideoInfoRemoteResult
        data object ParsingJSONError : GetVideoInfoRemoteResult
        data object UnknownError : GetVideoInfoRemoteResult
        data class Success(val videoInfo: VideoInfoEntity) : GetVideoInfoRemoteResult

    }

}