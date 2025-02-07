package io.involvedapps.repositories.video.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.random.Random

@Serializable
data class VideoInfoEntity(
    @SerialName("title") val title: String,
    @SerialName("thumbnails") val thumbnails: ThumbnailsEntity,
    @SerialName("qualities") val qualities: QualitiesEntity,
) {

    @Serializable
    data class ThumbnailsEntity(
        @SerialName("360") val thumbnail360Url: String,
    )

    @Serializable
    data class QualitiesEntity(
        @SerialName("auto") val qualities: List<QualityEntity>,
    ) {
        @Serializable
        data class QualityEntity(
            @SerialName("type") val type: String,
            @SerialName("url") val url: String,
        )
    }

}

fun Random.nextVideoInfoEntity() = VideoInfoEntity(
    title = nextLong().toString(),
    thumbnails = VideoInfoEntity.ThumbnailsEntity(
        thumbnail360Url = nextLong().toString(),
    ),
    qualities = VideoInfoEntity.QualitiesEntity(
        qualities = listOf(
            VideoInfoEntity.QualitiesEntity.QualityEntity(
                type = nextLong().toString(),
                url = nextLong().toString(),
            ),
        ),
    ),
)
